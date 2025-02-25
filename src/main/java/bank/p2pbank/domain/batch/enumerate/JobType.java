package bank.p2pbank.domain.batch.enumerate;

public enum JobType {
    INTEREST_PAYMENT,          // 이자 지급 배치
    MONTHLY_SETTLEMENT,        // 월별 정산 배치
    TRANSACTION_RECONCILIATION, // 거래 조정 배치 (데이터 무결성 체크)
    NH_BANK_SYNC,              // NH농협 API와 계좌 잔액 동기화
    ACCOUNT_CLEANUP,           // 오래된 계좌 정리
    TRANSACTION_CLEANUP        // 오래된 거래 내역 삭제
}
