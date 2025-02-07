package bank.p2pbank.user_sevice.entity;

import bank.p2pbank.common.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "bigint")
    private Long id;

    @Column(name = "email", nullable = false, columnDefinition = "varchar(320)", unique = true) //unique- 중복불가
    private String email;

    @JsonIgnore //JSON 직렬화에서 제외한다
    @Column(name = "password",nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @Builder
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
