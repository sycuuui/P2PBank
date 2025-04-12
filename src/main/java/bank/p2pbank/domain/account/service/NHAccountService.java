package bank.p2pbank.domain.account.service;

import bank.p2pbank.domain.account.dto.request.NHDepositorRequest;
import bank.p2pbank.domain.account.dto.response.NHBalanceResponse;
import bank.p2pbank.domain.account.dto.response.NHDepositorResponse;
import bank.p2pbank.domain.account.entity.NHAccount;
import bank.p2pbank.domain.account.factory.NHAccountFactory;
import bank.p2pbank.domain.account.repository.NHAccountRepository;
import bank.p2pbank.domain.account.util.NHAccountMapper;
import bank.p2pbank.domain.account.validator.NHAccountValidator;
import bank.p2pbank.domain.user.entity.User;
import bank.p2pbank.global.exception.ApplicationException;
import bank.p2pbank.global.exception.ErrorCode;
import bank.p2pbank.global.security.encrytion.AESUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class NHAccountService {
    private final NHApiService nhApiService;
    private final NHAccountMapper nhAccountMapper;
    private final NHAccountRepository nhAccountRepository;
    private final NHAccountValidator nhAccountValidator;
    private final NHAccountFactory nhAccountFactory;
    private final AESUtil aesUtil;

    @Transactional
    public void registerAccount(User user, NHDepositorRequest nhDepositorRequest) throws Exception {
        String bankcode = nhDepositorRequest.bankcode();
        String accountNumber = nhDepositorRequest.accountNumber();
        String birth = user.getBirth();

        //농협 open API로 예금주 조회
        Map<String, Object> depositor = nhApiService.inquireDepositor(bankcode, accountNumber);
        //필요한 데이터 뽑아내기(이름,계좌번호,bank)
        NHDepositorResponse nhDepositorResponse = nhAccountMapper.toNHDepositorResponse(depositor);

        nhAccountValidator.validateOwnerName(nhDepositorResponse.name(), user.getName());
        nhAccountValidator.validateDuplicateAccount(user, bankcode, accountNumber);

        //finAccount 등록
        Map<String, Object> openFinAccount = nhApiService.openFinAccountDirect("Y", birth, bankcode, accountNumber);
        //필요한 데이터 뽑아내기(등록번호)
        String registrationNumber = nhAccountMapper.toNHFinAccountResponse(openFinAccount);

        //finAccount 확인
        Map<String, Object> checkOpenFinAccount = nhApiService.checkOpenFinAccountDirect(registrationNumber, birth);
        //필요한 데이터 뽑아내기(등록번호)
        String finAccount = nhAccountMapper.toCheckNHFinAccountResponse(checkOpenFinAccount);

        String encryptedAccountNumber = aesUtil.encrypt(nhDepositorResponse.accountNumber());

        NHAccount nhAccount = nhAccountFactory.create(
                encryptedAccountNumber,
                nhDepositorResponse.code(),
                finAccount,
                registrationNumber,
                user
        );

        nhAccountRepository.save(nhAccount);
    }

    public String getBalance(User user, NHDepositorRequest nhDepositorRequest) {
        String bankcode = nhDepositorRequest.bankcode();
        String accountNumber = nhDepositorRequest.accountNumber();

        if (!nhAccountRepository.existsByUserAndBankcodeAndAccountNumber(user, bankcode, accountNumber)) {
            throw new ApplicationException(ErrorCode.NOT_FOUND_ACCOUNT_EXCEPTION);
        }

        //농협 open API로 계좌 조회
        Map<String, Object> depositorBalance = nhApiService.inquireBalance(user.getName(), bankcode, accountNumber);
        //필요한 데이터 뽑아내기
        NHBalanceResponse nhBalanceResponse = nhAccountMapper.toNHBalanceResponse(depositorBalance);

        return nhBalanceResponse.balance();
    }
}
