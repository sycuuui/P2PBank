# P2PBank

<h2> 1️⃣ JWT란? </h2>

<strong> JSON Web Token은 사용자의 정보를 안전하게 저장하기 위한 토큰 </strong>
<br>
-> accessToken & refreshToken 사용하여 사용 기간 만료 후에도 재발급에 용이하도록 구현

<h2> 2️⃣ JWT Filter </h2>
<strong>웹 서비스에서 요청이 들어올 때마다 무조건 한번씩 실행</strong>

1. token 유효성 검사 후 SecurityContext에 PrincipalDetails 객체 형태로 사용자 정보 저장
2. 다음 보안 필터 체인으로 요청과 응답 전달

<h2> 3️⃣ JWT AccessDeniedHandler </h2>
<strong>접근 권한이 없는 사용자가 접근 시 접근 실패 핸들링</strong>