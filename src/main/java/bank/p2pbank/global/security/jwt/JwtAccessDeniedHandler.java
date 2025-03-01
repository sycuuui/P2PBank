package bank.p2pbank.global.security.jwt;

import bank.p2pbank.global.common.ApplicationResponse;
import bank.p2pbank.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

//접근 권한이 없는 사용자가 접근 시 접근 실패 핸들러
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ApplicationResponse<Void> errorResponse = ApplicationResponse.error(ErrorCode.FORBIDDEN_EXCEPTION);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
