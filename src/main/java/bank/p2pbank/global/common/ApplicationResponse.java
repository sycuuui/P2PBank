package bank.p2pbank.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
//공통 응답 클래스 - 성공 응답과 오류 응답을 구분하여 처리
public record ApplicationResponse<T>(
        LocalDateTime timestamp,
        Integer code,
        String message,
        T data
) {
    public ApplicationResponse(BaseResponseCode responseCode, T data) {
        this(LocalDateTime.now(), responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static <T> ApplicationResponse<T> success(SuccessCode successCode, T data) {
        return new ApplicationResponse<>(successCode, data);
    }

    public static ApplicationResponse<Void> success(SuccessCode successCode) {
        return new ApplicationResponse<>(successCode, null);
    }

    public static ApplicationResponse<Void> error(ErrorCode errorCode) {
        return new ApplicationResponse<>(errorCode, null);
    }

    public static ApplicationResponse<Void> error(ErrorCode errorCode, String customMessage) {
        return new ApplicationResponse<>(new ErrorCode(errorCode.getHttpStatus(), errorCode.getCode(), customMessage), null);
    }
}
