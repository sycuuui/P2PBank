package bank.p2pbank.domain.user.service;

import bank.p2pbank.domain.user.dto.request.LoginRequest;
import bank.p2pbank.domain.user.dto.request.RegisterRequest;
import bank.p2pbank.domain.user.entity.User;
import bank.p2pbank.domain.user.enumerate.Role;
import bank.p2pbank.domain.user.repository.UserRepository;
import bank.p2pbank.global.exception.ApplicationException;
import bank.p2pbank.global.exception.ErrorCode;
import bank.p2pbank.global.security.jwt.TokenProvider;
import bank.p2pbank.global.security.jwt.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        Role role = Role.of(registerRequest.role());
        Optional<User> exsitUser = userRepository.findUserByEmailAndRoleAndDeletedAtIsNull(registerRequest.email(), role);

        if (exsitUser.isPresent()) {
            log.warn("존재하는 사용자. email={}, role={}", registerRequest.email(), role);
            throw new ApplicationException(ErrorCode.ALREADY_EXIST_EXCEPTION);
        }

        User user = User.builder()
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .name(registerRequest.name())
                .role(role)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public TokenDto login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findUserByEmailAndDeletedAtIsNull(loginRequest.email());

        if (user.isEmpty()) {
            log.warn("존재하지 않은 사용자. email={}", loginRequest.email());
            throw new ApplicationException(ErrorCode.ALREADY_EXIST_EXCEPTION);
        }

        if (!passwordEncoder.matches(loginRequest.password(), user.get().getPassword())) {
            throw new ApplicationException(ErrorCode.WRONG_PASSWORD_EXCEPTION);
        }

        TokenDto tokenDto = tokenProvider.createToken(user.get());

        return tokenDto;
    }
}
