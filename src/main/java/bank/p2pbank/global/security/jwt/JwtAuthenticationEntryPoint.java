package bank.p2pbank.global.security.jwt;

import bank.p2pbank.global.common.ApplicationResponse;
import bank.p2pbank.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

//인증되지 않은 사용자가 접근 시 인증 실패 핸들러
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    //security에서 감지(401처리)하여 JwtAuthenticationEntryPoint 호출 -> commence() 실행 -> http 응답
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApplicationResponse<Void> errorResponse = ApplicationResponse.error(ErrorCode.UNAUTHORIZED_EXCEPTION);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}

