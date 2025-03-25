package bank.p2pbank.domain.user.dto.request;

import bank.p2pbank.domain.user.enumerate.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterRequest(
        @NotNull @Email
        String email,
        @NotNull @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String password,
        @NotNull
        String name,
        @NotNull
        String role,
        @NotNull @Size(min = 8,max = 8)
        String birth
) {
}
