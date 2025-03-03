package bank.p2pbank.domain.user.service;

import bank.p2pbank.domain.user.dto.request.RegisterRequest;
import bank.p2pbank.domain.user.entity.User;
import bank.p2pbank.domain.user.repository.UserRepository;
import bank.p2pbank.global.exception.ApplicationException;
import bank.p2pbank.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks //테스트 대상인 AuthService 주입
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void register_success() {
        //given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("test@test.com")
                .name("서연")
                .password("password123!")
                .role("ADMIN")
                .build();
        given(userRepository.findUserByEmailAndRoleAndDeletedAtIsNull(any(), any()))
                .willReturn(Optional.empty());
        given(passwordEncoder.encode(any()))
                .willReturn("encoded_password");

        //when
        authService.register(registerRequest);

        // then
        verify(userRepository, times(1)).save(any()); //save()가 한번 호풀 되어있는지 확인
    }

    @Test
    @DisplayName("이미 존재하는 사용자로 인한 회원가입 실패 테스트")
    void register_fail_alreadyExists() {
        // given
        RegisterRequest request = new RegisterRequest("user@example.com", "password123!", "홍길동", "ADMIN");
        given(userRepository.findUserByEmailAndRoleAndDeletedAtIsNull(any(), any()))
                .willReturn(Optional.of(mock(User.class)));

        // when & then
        ApplicationException exception = assertThrows(ApplicationException.class, () ->
                authService.register(request));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ALREADY_EXIST_EXCEPTION);
        verify(userRepository, never()).save(any());
    }

}
