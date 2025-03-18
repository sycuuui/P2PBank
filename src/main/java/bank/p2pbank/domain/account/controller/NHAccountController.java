package bank.p2pbank.domain.account.controller;

import bank.p2pbank.domain.account.service.NHApiService;
import bank.p2pbank.global.common.ApplicationResponse;
import bank.p2pbank.global.success.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/nh")
@RequiredArgsConstructor
public class NHAccountController {

    private final NHApiService nhApiService;

    /**
     * 예금주 조회 API 엔드포인트
     */
    @GetMapping("/depositor")
    public ApplicationResponse<Map<String, Object>> inquireDepositor(@RequestParam String bankCode, @RequestParam String accountNumber) {
        Map<String, Object> response = nhApiService.inquireDepositor(bankCode, accountNumber);
        return ApplicationResponse.success(SuccessCode.NHACCOUNT_DEPOSITOR,response);
    }
}