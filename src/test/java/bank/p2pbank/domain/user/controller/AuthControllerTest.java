package bank.p2pbank.domain.user.controller;

import bank.p2pbank.domain.user.dto.request.RegisterRequest;
import bank.p2pbank.domain.user.entity.User;
import bank.p2pbank.domain.user.enumerate.Role;
import bank.p2pbank.domain.user.repository.UserRepository;
import bank.p2pbank.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.30")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

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
}
