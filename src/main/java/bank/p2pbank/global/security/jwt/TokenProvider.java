package bank.p2pbank.global.security.jwt;

import bank.p2pbank.domain.user.entity.User;
import bank.p2pbank.domain.user.enumerate.Role;
import bank.p2pbank.domain.user.repository.UserRepository;
import bank.p2pbank.global.exception.ApplicationException;
import bank.p2pbank.global.exception.ErrorCode;
import bank.p2pbank.global.security.PrincipalDetails;
import bank.p2pbank.global.security.jwt.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    private final UserRepository userRepository;

    // AccessToken (1일)
    private static final long ACCESS_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000L;

    // RefreshToken (30일)
    private static final long REFRESH_TOKEN_EXPIRATION = 30 * 24 * 60 * 60 * 1000L;

    @PostConstruct
    protected void init() {
        byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    // AccessToken 생성
    public String createAccessToken(User user) {
        Claims claims = getClaims(user);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // RefreshToken 생성
    public String createRefreshToken(User user) {
        Claims claims = getClaims(user);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // AccessToken + RefreshToken 발급
    public TokenDto createToken(User user) {
        return TokenDto.builder()
                .accessToken(createAccessToken(user))
                .refreshToken(createRefreshToken(user))
                .build();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.WRONG_TOKEN);
        }
    }

    public TokenDto reissue(User user, String refreshToken) {
        // 액세스 토큰 재발급
        String accessToken = createAccessToken(user);

        // 리프레쉬 토큰 재발급 조건 및 로직
        if(getExpiration(refreshToken) <= getExpiration(accessToken)) {
            refreshToken = createRefreshToken(user);
        }

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 토큰에서 이메일 추출
    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰에서 역할 추출
    public Role getRole(String token) {
        return Role.valueOf((String)Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role"));
    }

    /**
     * 토큰의 만료기한 반환
     * @param token - 일반적으로 액세스 토큰 / 토큰 재발급 요청 시에는 리프레쉬 토큰이 들어옴
     * @return 해당 토큰의 만료정보를 반환
     */
    public Long getExpiration(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().getTime();
    }

    /**
     * 토큰에서 정보를 추출해서 Authentication 객체를 반환
     * @param token - 액세스 토큰으로, 해당 토큰에서 정보를 추출해서 사용
     * @return 토큰 정보와 일치하는 Authentication 객체 반환
     */
    public Authentication getAuthentication(String token) {
        String email = getEmail(token);
        Role role = getRole(token);
        User user = userRepository.findUserByEmailAndRoleAndDeletedAtIsNull(email,role).orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));
        PrincipalDetails principalDetails = new PrincipalDetails(user);

        return new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
    }

    /**
     * Claims 정보 생성
     * @param user - 사용자 정보 중 사용자를 구분할 수 있는 정보 두 개를 활용함
     * @return 사용자 구분 정보인 이메일과 역할을 저장한 Claims 객체 반환
     */
    private Claims getClaims(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("role", user.getRole());

        return claims;
    }
}
