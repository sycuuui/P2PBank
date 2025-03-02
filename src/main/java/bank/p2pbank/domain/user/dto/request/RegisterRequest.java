package bank.p2pbank.domain.user.dto.request;

import bank.p2pbank.domain.user.enumerate.Role;

public record RegisterRequest(
        String email,
        String password,
        String name,
        Role role
) {
}
