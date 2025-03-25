package bank.p2pbank.domain.account.dto.request;

import org.springframework.web.bind.annotation.RequestParam;

public record OpenFinAccountDirectRequest(
        String bankCode,
        String accountNumber,
        boolean isWithdrawalAccount,
        String birthbrno
) {
}
