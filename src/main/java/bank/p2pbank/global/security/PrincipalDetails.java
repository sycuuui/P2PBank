package bank.p2pbank.global.security;

import bank.p2pbank.domain.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//로그인한 사용자 정보를 저장하고 관리
//UserDetails - 로그인한 사용자 정보를 담기 위한 표준 인터페이스. 로그인 성공 후, SecurityContext에 저장되는 객체: UserDetails 타입
//OAuth2User - 소셜 로그인시, 소셜 서비스에서 제공하는 사용자 정보를 담기 위한 인터페이스.
public class PrincipalDetails implements UserDetails, OAuth2User {

    @Getter
    private final User user;
    private Map<String, Object> attributes; //소셜 로그인시 소셜 서비스에서 제공하는 정보 담는 필드

    // 일반 로그인 (User 정보로 PrincipalDetaiils 객체 생성)
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // OAuth 로그인 (attributes 정보로 PrincipalDetaiils 객체 생성)
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ user.getRole()));//ROLE_ 을 앞에 붙이느냐 마냐에 따라서 ROLE과 Authority가 갈린다(스프링 시큐리티에서 권장하는 권한 네이밍 규칙)

        return authorities;
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
