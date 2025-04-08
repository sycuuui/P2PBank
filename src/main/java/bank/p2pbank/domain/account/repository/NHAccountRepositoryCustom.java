package bank.p2pbank.domain.account.repository;

import bank.p2pbank.domain.user.entity.User;

public interface NHAccountRepositoryCustom {
    boolean existsByUserAndAccountNumber(User user, String nhAccountNumber);
}
