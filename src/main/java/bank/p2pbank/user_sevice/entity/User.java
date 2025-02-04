package bank.p2pbank.user_sevice.entity;

import bank.p2pbank.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "bigint")
    private Long id;

    @Column(name = "email", nullable = false, columnDefinition = "varchar(320)")
    private String email;

    @Column(name = "password", columnDefinition = "varchar(255)")
    private String password;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(50)")
    private String name;
}
