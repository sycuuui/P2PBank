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

//접근 권한이 없는 사용자가 접근 시 접근 실패 핸들링
// AccessDeniedHandlerImpl는 HTML 응답을 반환하기 때문에, REST API 서버이므로 커스텀 핸들러 생성
@Component
@RequiredArgsConstructor //final 필드에 대한 생성자 자동 생성
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper; //자바 객체를 JSON 문자열로 변환

    //security에서 감지(403처리)하여 JwtAccessDeniedHandler 호출 -> handler() 실행 -> http 응답
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ApplicationResponse<Void> errorResponse = ApplicationResponse.error(ErrorCode.FORBIDDEN_EXCEPTION);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
