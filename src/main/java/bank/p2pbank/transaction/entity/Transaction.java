package bank.p2pbank.transaction.entity;

import bank.p2pbank.account.entity.Account;
import bank.p2pbank.common.BaseTimeEntity;
import bank.p2pbank.transaction.enumerate.TransactionStatus;
import bank.p2pbank.transaction.enumerate.TransactionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "bigint")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_id", nullable = false)
    private Account senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_account_id", nullable = false)
    private Account receiverAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionStatus status;

    // Optimistic Locking (이중 송금 방지)
    @Version //객체가 변경될 때마다 자동으로 값이 증가
    private Integer version;

    @Builder
    public Transaction(Account senderAccount, Account receiverAccount, BigDecimal amount, TransactionType transactionType, TransactionStatus status) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.transactionType = transactionType;
        this.status = status;
    }

    public void updateStatus(TransactionStatus status) {
        this.status = status;
    }
}
