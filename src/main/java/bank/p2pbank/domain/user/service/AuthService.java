package bank.p2pbank.domain.user.service;

import bank.p2pbank.domain.user.dto.request.RegisterRequest;
import bank.p2pbank.domain.user.entity.User;
import bank.p2pbank.domain.user.enumerate.Role;
import bank.p2pbank.domain.user.repository.UserRepository;
import bank.p2pbank.global.exception.ApplicationException;
import bank.p2pbank.global.exception.ErrorCode;
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

}
