package bank.p2pbank.domain.user.entity;

import bank.p2pbank.domain.user.dto.request.RegisterRequest;
import bank.p2pbank.domain.user.enumerate.Role;
import bank.p2pbank.global.common.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
    @Column(name = "password", nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @Column(name = "birth", nullable = false, columnDefinition = "varchar(10)")
    private String birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Builder
    public User(String email, String password, String name, String birth, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.role = role;
    }
}
