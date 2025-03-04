package bank.p2pbank.domain.user.controller;

import bank.p2pbank.domain.user.dto.request.LoginRequest;
import bank.p2pbank.domain.user.dto.request.RegisterRequest;
import bank.p2pbank.domain.user.service.AuthService;
import bank.p2pbank.global.common.ApplicationResponse;
import bank.p2pbank.global.security.jwt.dto.TokenDto;
import bank.p2pbank.global.success.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ApplicationResponse<Void> register(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);

        return ApplicationResponse.success(SuccessCode.CREATED);
    }

    @PostMapping("login")
    public ApplicationResponse<TokenDto> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenDto tokenDto = authService.login(loginRequest);

        return ApplicationResponse.success(SuccessCode.LOGIN,tokenDto);
    }
}
