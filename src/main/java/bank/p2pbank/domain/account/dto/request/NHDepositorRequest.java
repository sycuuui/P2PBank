package bank.p2pbank.domain.account.dto.request;

public record NHDepositorRequest(
        String bankCode,
        String accountNumber
) {
}
