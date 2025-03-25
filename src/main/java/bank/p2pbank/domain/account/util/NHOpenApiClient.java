package bank.p2pbank.domain.account.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "NHOpenApi", url = "${nhapi.base-uri}")
public interface NHOpenApiClient {
    //계좌정보 확인
    @PostMapping(value = "/InquireDepositorAccountNumber.nh", consumes = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> inquireDepositor(@RequestBody Map<String, Object> request);

    //잔액 확인
    @PostMapping(value = "/InquireBalance.nh", consumes = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> inquireBalance(@RequestBody Map<String, Object> request);

    //finAccount 발급
    @PostMapping(value = "/OpenFinAccountDirect.nh", consumes = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> OpenFinAccountDirect(@RequestBody Map<String, Object> request);

    //finAccount 발급확인
    @PostMapping(value = "/CheckOpenFinAccountDirect.nh", consumes = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> CheckOpenFinAccountDirect(@RequestBody Map<String, Object> request);

}
