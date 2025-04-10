package bank.p2pbank.domain.account.validator;

import bank.p2pbank.domain.account.repository.NHAccountRepository;
import bank.p2pbank.domain.user.entity.User;
import bank.p2pbank.global.exception.ApplicationException;
import bank.p2pbank.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NHAccountValidator {
    private final NHAccountRepository nhAccountRepository;

    public void validateOwnerName(String depositorName, String userName) {
        if (!depositorName.equals(userName)) {
            throw new ApplicationException(ErrorCode.WRONG_DEPOSIT_USER_EXEPTION);
        }
    }

    public void validateDuplicateAccount(User user, String bankCode, String accountNumber) {
        if (nhAccountRepository.existsByUserAndBankcodeAndAccountNumber(user, bankCode, accountNumber)) {
            throw new ApplicationException(ErrorCode.ALREADY_REGISTERED_ACCOUNT_EXCEPTION);
        }
    }
}
