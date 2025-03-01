package bank.p2pbank.global.security;

import bank.p2pbank.domain.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

    @Getter
    private final User user;
    private Map<String, Object> attributes;

    // 일반 로그인
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // OAuth 로그인
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // 사용자의 비밀번호 반환
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 사용자의 이름 반환
    @Override
    public String getUsername() {
        return user.getName();
    }

    // 계정이 잠기지 않았으므로 true 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 패스워드가 만료되지 않았으므로 true 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계속 사용 가능한 것이기에 true 반환
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
