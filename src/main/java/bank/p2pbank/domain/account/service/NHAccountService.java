package bank.p2pbank.domain.account.service;

import bank.p2pbank.domain.account.dto.response.NHDepositorResponse;
import bank.p2pbank.domain.account.entity.NHAccount;
import bank.p2pbank.domain.account.repository.NHAccountRepository;
import bank.p2pbank.domain.account.util.NHAccountMapper;
import bank.p2pbank.domain.user.entity.User;
import bank.p2pbank.global.exception.ApplicationException;
import bank.p2pbank.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NHAccountService {
    private final NHApiService nhApiService;
    private final NHAccountMapper nhAccountMapper;
    private final NHAccountRepository nhAccountRepository;

    public void registerAccount(User user, String bankCode, String accountNumber) {
        //농협 open API로 예금주 조회
        Map<String, Object> depositor = nhApiService.inquireDepositor(bankCode, accountNumber);
        //필요한 데이터 뽑아내기(이름,계좌번호,bank)
        NHDepositorResponse nhDepositorResponse = nhAccountMapper.toNHDepositorResponse(depositor);

        if (!nhDepositorResponse.name().equals(user.getName())) {
            throw new ApplicationException(ErrorCode.WRONG_DEPOSIT_USER_EXEPTION);
        }

        NHAccount nhAccount = NHAccount.builder()
                .bankCode(nhDepositorResponse.code())
                .nhAccountNumber(nhDepositorResponse.accountNumber())
                .user(user)
                .build();

        nhAccountRepository.save(nhAccount);
    }
}
