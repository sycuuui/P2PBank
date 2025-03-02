package bank.p2pbank.domain.user.dto.request;

import bank.p2pbank.domain.user.enumerate.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotNull @Email
        String email,
        @NotNull
        String password,
        @NotNull
        String name,
        @NotNull
        String role
) {
}
