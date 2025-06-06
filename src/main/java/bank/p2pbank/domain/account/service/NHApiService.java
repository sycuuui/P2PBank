package bank.p2pbank.domain.account.service;

import bank.p2pbank.domain.account.util.NHOpenApiClient;
import bank.p2pbank.global.exception.ApplicationException;
import bank.p2pbank.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NHApiService {
    private final NHOpenApiClient nhOpenApiClient;

    @Value("${nhapi.iscd}")
    String iscd;

    @Value("${nhapi.accessToken}")
    String accessToken;

    /**
     * 예금주 조회 API 요청
     */
    public Map<String, Object> inquireDepositor(String bankcode, String accountNumber) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("Header", createHeader("InquireDepositorAccountNumber")); // 공통 Header 추가
        requestBody.put("Bncd", bankcode);   // 은행 코드 (농협: 011)
        requestBody.put("Acno", accountNumber);  // 계좌 번호

        Map<String, Object> response = nhOpenApiClient.inquireDepositor(requestBody);

        if(response.get("Dpnm")==null || response.get("Acno")==null) {
            throw new ApplicationException(ErrorCode.WRONG_DEPOSIT_INVALID_VALUE_EXEPTION); // 커스텀 예외
        }

        return response;
    }

    /**
     * 계좌 잔액 API 요청
     */
    public Map<String, Object> inquireBalance(String userName, String bankcode, String accountNumber){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("Header",createHeader("InquireBalance"));
        requestBody.put("Bncd", bankcode);   // 은행 코드 (농협: 011)
        requestBody.put("Acno", accountNumber);  // 계좌 번호
        requestBody.put("Dpnm", userName);

        Map<String, Object> response =  nhOpenApiClient.inquireBalance(requestBody);

        return response;
    }

    /**
     * FinAccount 직접발급
     */
    public Map<String, Object> openFinAccountDirect(String isWithdrawalAccount, String birth, String bankcode, String accountNumber){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("Header",createHeader("OpenFinAccountDirect"));
        requestBody.put("DrtrRgyn", isWithdrawalAccount); //출금이체 등록 여부
        requestBody.put("BrdtBrno", birth); //생년월일(사업자번호) 개인:YYYYMMDD, 기업: 사업자 번호
        requestBody.put("Bncd", bankcode);   // 은행 코드 (농협: 011)
        requestBody.put("Acno", accountNumber);  // 계좌 번호

        Map<String, Object> response =  nhOpenApiClient.openFinAccountDirect(requestBody);

        if(response.get("Rgno")==null) {
            throw new ApplicationException(ErrorCode.WRONG_DEPOSIT_INVALID_VALUE_EXEPTION); // 커스텀 예외
        }

        return response;
    }

    /**
     * FinAccount 직접발급
     */
    public Map<String, Object> checkOpenFinAccountDirect(String registrationNumber, String birth){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("Header",createHeader("CheckOpenFinAccountDirect"));
        requestBody.put("Rgno", registrationNumber); //등록번호
        requestBody.put("BrdtBrno", birth); //생년월일(사업자번호) 개인:YYYYMMDD, 기업: 사업자 번호

        Map<String, Object> response =  nhOpenApiClient.checkOpenFinAccountDirect(requestBody);

        if(response.get("FinAcno")==null) {
            throw new ApplicationException(ErrorCode.WRONG_DEPOSIT_INVALID_VALUE_EXEPTION); // 커스텀 예외
        }

        return response;
    }

    /**
     * NH농협 API 공통 Header 생성
     */
    private Map<String, String> createHeader(String apiName) {
        Map<String, String> header = new HashMap<>();
        header.put("ApiNm", apiName);
        header.put("Tsymd", getCurrentDate());  // 현재 날짜 (YYYYMMDD)
        header.put("Trtm", getCurrentTime());  // 현재 시간 (HHMMSS)
        header.put("Iscd", iscd);
        header.put("FintechApsno", "001");  // 기본값
        header.put("ApiSvcCd", "DrawingTransferA");  // 서비스 코드
        header.put("IsTuno", generateTransactionId());  // 거래 ID (고유 값)
        header.put("AccessToken", accessToken);  // NH농협 AccessToken

        return header;
    }

    /**
     * 현재 날짜 (YYYYMMDD) 반환
     */
    private String getCurrentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 현재 시간 (HHMMSS) 반환
     */
    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
    }

    /**
     * 거래 고유 ID 생성
     */
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
}
