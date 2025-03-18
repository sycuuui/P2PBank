package bank.p2pbank.global.util.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "NHApiClient", url = "${nhapi.base-uri}")
public interface NHOpenApiClient {
    @PostMapping(value = "/InquireDepositorAccountNumber.nh", consumes = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> inquireDepositor(@RequestBody Map<String, Object> request);
}
