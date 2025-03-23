package bank.p2pbank.global.exception;

import bank.p2pbank.global.common.BaseResponseCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ErrorCode implements BaseResponseCode {
    INTERNAL_SERVER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, 2000, "예기치 못한 오류가 발생했습니다."),
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, 2001, "존재하지 않는 리소스입니다."),
    INVALID_VALUE_EXCEPTION(HttpStatus.BAD_REQUEST, 2002, "올바르지 않은 요청 값입니다."),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, 2003, "권한이 없는 요청입니다."),
    ALREADY_DELETE_EXCEPTION(HttpStatus.BAD_REQUEST, 2004, "이미 삭제된 리소스입니다."),
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, 2005, "인가되지 않는 요청입니다."),
    ALREADY_EXIST_EXCEPTION(HttpStatus.BAD_REQUEST, 2006, "이미 존재하는 리소스입니다."),
    INVALID_SORT_EXCEPTION(HttpStatus.BAD_REQUEST, 2007, "올바르지 않은 정렬 값입니다."),

    // 3000: Auth Error (3100~)
    NOT_FOUND_USER_EXCEPTION(HttpStatus.NOT_FOUND, 3100, "존재하지 않는 사용자입니다."),
    WRONG_PASSWORD_EXCEPTION(HttpStatus.BAD_REQUEST, 3101, "유효하지 않은 비밀번호입니다."),
    WRONG_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, 3102, "유효하지 않은 토큰입니다."),
    LOGOUT_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, 3103, "로그아웃된 토큰입니다"),
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED, 3104, "유효하지 않은 토큰입니다."),

    // 4000: User Error (4100~)

    // 5000: Account Error (5100~)
    WRONG_DEPOSIT_USER_EXEPTION(HttpStatus.UNAUTHORIZED, 5100, "사용자와 계정주가 일치하지 않습니다.");

    // 6000: Batch Error (6100~)

    // 7000: Transaction Error (7100~)

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
