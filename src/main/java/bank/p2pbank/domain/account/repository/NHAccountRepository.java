package bank.p2pbank.domain.account.repository;

import bank.p2pbank.domain.account.entity.NHAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NHAccountRepository extends JpaRepository<NHAccount, Long>, NHAccountRepositoryCustom {
    //만약 user와 같은 NHAccount에서 finAccount가 있으면 안됌,,?...으음...
}
