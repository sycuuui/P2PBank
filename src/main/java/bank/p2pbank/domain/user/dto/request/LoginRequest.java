package bank.p2pbank.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull @Email
        String email,

        @NotNull @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String password
) {
}
