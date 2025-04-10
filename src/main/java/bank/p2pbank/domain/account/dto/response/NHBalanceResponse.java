package bank.p2pbank.domain.account.dto.response;

import lombok.Builder;

@Builder
public record NHBalanceResponse(
        String balance,
        String ldbl,
        String finAcno
) {
}
