package bank.p2pbank.domain.user.repository;

import bank.p2pbank.domain.user.entity.User;
import bank.p2pbank.domain.user.enumerate.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * email,role을 기준으로 사용자 찾는다
     * @param email 이메일
     * @param role 역할
     * @return Optional<User></User></User>
     */
    Optional<User> findUserByEmailAndRoleAndDeletedAtIsNull(String email, Role role);

    /**
     * email 기준으로 사용자 찾는다
     * @param email 이메일
     * @return Optional<User></User>
     */
    Optional<User> findUserByEmailAndDeletedAtIsNull(String email);
}
