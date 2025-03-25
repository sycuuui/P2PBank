package bank.p2pbank.domain.account.dto.request;

public record NHDepositorRequest(
        String bankcode,
        String accountNumber
) {
}
