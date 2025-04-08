package bank.p2pbank.domain.account.repository;

import bank.p2pbank.domain.user.entity.User;

public interface NHAccountRepositoryCustom {
    boolean existsByUserAndBankcodeAndAccountNumber(User user, String bankcode, String nhAccountNumber);
}
