package bank.p2pbank.domain.account.factory;

import bank.p2pbank.domain.account.entity.NHAccount;
import bank.p2pbank.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class NHAccountFactory {
    public NHAccount create(String nhAccountNumber, String bankCode, String finAccount, String registrationNumber, User user) {
        return NHAccount.builder()
                .nhAccountNumber(nhAccountNumber)
                .bankCode(bankCode)
                .finAccount(finAccount)
                .registrationNumber(registrationNumber)
                .user(user)
                .build();
    }
}
