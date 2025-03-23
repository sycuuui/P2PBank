package bank.p2pbank.domain.account.dto.response;

import lombok.Builder;

@Builder
public record NHDepositorResponse(
        String name,
        String code,
        String accountNumber
) {
}
