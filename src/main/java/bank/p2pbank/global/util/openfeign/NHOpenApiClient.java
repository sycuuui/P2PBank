package bank.p2pbank.global.util.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "NHApiClient", url = "${nh.api.base-uri}")
public class NHOpenApiClient {
    @GetMapping("/InquireBalance")
    NHBalanceResponse inquireBalance(
            @RequestHeader("ApiNm") String apiName,
            @RequestHeader("Tsymd") String requestDate,
            @RequestHeader("Trtm") String requestTime,
            @RequestHeader("Iscd") String institutionCode,
            @RequestHeader("FintechApsno") String fintechNumber,
            @RequestHeader("ApiSvcCd") String apiServiceCode,
            @RequestHeader("IsTuno") String transactionUniqueNumber,
            @RequestHeader("AccessToken") String accessToken,
            @RequestParam("FinAcno") String fintechAccountNumber
    );
}
