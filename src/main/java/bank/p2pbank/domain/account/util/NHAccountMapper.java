package bank.p2pbank.domain.account.util;


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
}
