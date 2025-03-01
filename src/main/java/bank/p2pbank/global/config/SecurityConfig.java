package bank.p2pbank.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //스프링의 설정 클래스임을 나타냄. 해당 어노테이션은 @Component와 같은 역할을 하며, 스프링이 이 클래스를 설정 클래스로 인식하여 빈들을 등록하도록 함
@EnableWebSecurity //Spring Secuirty의 기본 설정을 활성화할 수 있음. 보안 필터 자동 적용
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 해시 알고리즘을 이용하여 비밀번호 암호화
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    } // AuthenticationManager: 로그인 요청이 들어오면 사용자의 아이디와 비밀번호를 검증하는 역할. 내부적으로 UserDetailsService와 PasswordEncoder를 사용하여 인증 수행
}
