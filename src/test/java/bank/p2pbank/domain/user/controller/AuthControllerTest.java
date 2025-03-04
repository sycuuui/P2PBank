package bank.p2pbank.domain.user.controller;

import bank.p2pbank.domain.user.dto.request.LoginRequest;
import bank.p2pbank.domain.user.dto.request.RegisterRequest;
import bank.p2pbank.domain.user.entity.User;
import bank.p2pbank.domain.user.enumerate.Role;
import bank.p2pbank.domain.user.repository.UserRepository;
import bank.p2pbank.global.config.TestContainerConfig;
import bank.p2pbank.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class AuthControllerTest extends TestContainerConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공 테스트")
    public void register_success() throws Exception {
        // given
        RegisterRequest request = new RegisterRequest(
                "test@example.com", "password123!", "test", "ADMIN"
        );

        // when & then
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("1001"))
                .andExpect(jsonPath("$.message").value("정상적으로 생성되었습니다."));
    }

    @Test
    @DisplayName("중복 회원가입 실패 테스트")
    public void register_fail_alreadyExists() throws Exception {
        // given
        userRepository.save(User.builder()
                .email("user@example.com")
                .password("encodedPassword")
                .name("홍길동")
                .role(Role.GENERAL)
                .build());

        RegisterRequest request = new RegisterRequest(
                "test@example.com", "password123!", "test", "ADMIN"
        );

        // when & then
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXIST_EXCEPTION.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXIST_EXCEPTION.getMessage()));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void login_success() throws Exception {
        // given
        User user = User.builder()
                .email("test@example.com")
                .password(passwordEncoder.encode("password123!"))  // 비번 암호화 저장
                .name("테스트유저")
                .role(Role.GENERAL)
                .build();

        userRepository.save(user);

        LoginRequest request = new LoginRequest("test@example.com", "password123!");

        // when & then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("1000"))
                .andExpect(jsonPath("$.message").value("정상 처리되었습니다."))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists());
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    public void login_fail_invalidPassword() throws Exception {
        // given
        User user = User.builder()
                .email("test@example.com")
                .password(passwordEncoder.encode("correctPassword"))  // 실제 저장 비번
                .name("테스트유저")
                .role(Role.GENERAL)
                .build();

        userRepository.save(user);

        LoginRequest request = new LoginRequest("test@example.com", "wrongPassword");  // 틀린 비번 요청

        // when & then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.WRONG_PASSWORD_EXCEPTION.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.WRONG_PASSWORD_EXCEPTION.getMessage()));
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 사용자")
    public void login_fail_userNotFound() throws Exception {
        // given
        LoginRequest request = new LoginRequest("notfound@example.com", "password123!");

        // when & then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND_USER_EXCEPTION.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }
}
