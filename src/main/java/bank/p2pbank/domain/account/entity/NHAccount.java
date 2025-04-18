package bank.p2pbank.domain.account.entity;

import bank.p2pbank.global.common.BaseTimeEntity;
import bank.p2pbank.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "nhAccount")
@NoArgsConstructor(access = AccessLevel.PROTECTED) //매개변수가 없는 기본 생성자를 자동으로 생성
public class NHAccount extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //JPA에서 엔티티의 기본 키를 자동 생성 //IDENTITY - DB의 AUTO_INCREMENT
    @Column(nullable = false, name = "id", columnDefinition = "bigint")
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String nhAccountNumber;

    @Column(nullable = false, unique = true, length = 30)
    private String finAccount;

    @Column(nullable = false, unique = true, length = 30)
    private String registrationNumber;

    @Column(nullable = false)
    private String bankCode;

    @Column(nullable = false)
    private BigDecimal nhBalance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public NHAccount(String nhAccountNumber, String finAccount, String registrationNumber, String bankCode, User user) {
        this.nhAccountNumber = nhAccountNumber;
        this.finAccount = finAccount;
        this.registrationNumber = registrationNumber;
        this.bankCode = bankCode;
        this.user = user;
        this.nhBalance = BigDecimal.ZERO;
    }

    public void updateBalance(BigDecimal newBalance) {
        this.nhBalance = newBalance;
    }
}
