package bank.p2pbank.global.success;

import bank.p2pbank.global.common.BaseResponseCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum SuccessCode implements BaseResponseCode {
    SUCCESS(HttpStatus.OK, 1000, "정상적인 요청입니다."),
    CREATED(HttpStatus.CREATED, 1001, "정상적으로 생성되었습니다."),

    // 3000: Auth Success (3000~3099)
    LOGIN(HttpStatus.OK, 3000, "정상적인 로그인 요청입니다.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    SuccessCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
