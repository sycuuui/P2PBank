package bank.p2pbank.domain.user.enumerate;

import bank.p2pbank.global.exception.ApplicationException;
import bank.p2pbank.global.exception.ErrorCode;

import java.util.Arrays;
import java.util.Locale;

public enum Role {
    GENERAL, ADMIN;

    public static Role of(String role) {
        String upperRole = role.toUpperCase(Locale.ROOT);

        return Arrays.stream(Role.values())
                .filter(r -> r.name().equals(upperRole))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_VALUE_EXCEPTION));
    }
}
