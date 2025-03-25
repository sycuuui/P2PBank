package bank.p2pbank.domain.account.util;


import bank.p2pbank.domain.account.dto.response.NHBalanceResponse;
import bank.p2pbank.domain.account.dto.response.NHDepositorResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component //컴포넌트 스캔 대상 클래스 (bean 자동 등록)
public class NHAccountMapper {
    public NHDepositorResponse toNHDepositorResponse(Map<String, Object> response) {
        try {
            String bankCode = (String) response.get("Bncd");
            String accountNumber = (String) response.get("Acno");
            String depositorName = (String) response.get("Dpnm");

            return NHDepositorResponse.builder()
                    .code(bankCode)
                    .name(depositorName)
                    .accountNumber(accountNumber)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("예금주 조회 응답 데이터 변환 실패");
        }
    }

    public NHBalanceResponse toNHBalanceResponse(Map<String, Object> response) {
        try {
            String balance = (String) response.get("RlpmAbamt");
            String ldbl = (String) response.get("Ldbl"); // 원장 잔액 - 표면상 존재하는 전체 금액 (이체 대기 중인 금액 포함)
            String finAcno = (String) response.get("FinAcno"); //핀테크 번호

            return NHBalanceResponse.builder()
                    .balance(balance)
                    .ldbl(ldbl)
                    .finAcno(finAcno)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("예금주 조회 응답 데이터 변환 실패");
        }
    }

    public String toNHFinAccountResponse(Map<String, Object> response) {
        try {
            return (String) response.get("Rgno");
        } catch (Exception e) {
            throw new RuntimeException("예금주 조회 응답 데이터 변환 실패");
        }
    }

    public String toCheckNHFinAccountResponse(Map<String, Object> response) {
        try {
            return (String) response.get("FinAcno");
        } catch (Exception e) {
            throw new RuntimeException("예금주 조회 응답 데이터 변환 실패");
        }
    }
}
