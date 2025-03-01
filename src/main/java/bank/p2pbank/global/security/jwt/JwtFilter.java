package bank.p2pbank.global.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    //웹 서비스에서 요청이 들어올 때마다 무조건 실행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(request);

        //token 유효성 검사 후 SecurityContext에 사용자 정보 저장
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //다음 보안 필터 체인으로 요청과 응답 전달
        //JWT필터를 거쳐 필터에서 통과되면 AuthenticationEntryPoint가 동작. 만약, 인증은 됐는데 권한이 부족한 경우  AccessDeniedHandler가 동작
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return StringUtils.hasText(token) && token.startsWith("Bearer ") ? token.substring(7) : null;
    }
}
