package bank.p2pbank.domain.user.repository;

import bank.p2pbank.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * email을 기준으로 사용자 찾는다
     * @param email 이메일
     * @return Optional<User></User>
     */
    Optional<User> findUserByEmail(String email);
}
