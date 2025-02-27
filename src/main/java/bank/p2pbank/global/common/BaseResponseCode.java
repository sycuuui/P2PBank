package bank.p2pbank.global.common;

import org.springframework.http.HttpStatus;

public interface BaseResponseCode {
    HttpStatus getHttpStatus();
    Integer getCode();
    String getMessage();
}
