package bank.p2pbank.domain.account.repository;

import bank.p2pbank.domain.account.entity.NHAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NHAccountRepository extends JpaRepository<NHAccount, Long> {
}
