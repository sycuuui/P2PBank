package bank.p2pbank.domain.account.controller;

import bank.p2pbank.domain.account.service.NHAccountService;
import bank.p2pbank.domain.account.service.NHApiService;
import bank.p2pbank.global.common.ApplicationResponse;
import bank.p2pbank.global.security.PrincipalDetails;
import bank.p2pbank.global.success.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/nh")
@RequiredArgsConstructor
public class NHAccountController {
    private final NHApiService nhApiService; //추후 삭제
    private final NHAccountService nhAccountService;

    /**
     * 예금주 조회 API 엔드포인트
     */
    @PostMapping("/depositor")
    public ApplicationResponse<Void> saveDepositor(@RequestParam String bankCode, @RequestParam String accountNumber, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        nhAccountService.registerAccount(principalDetails.getUser(), bankCode, accountNumber);
        return ApplicationResponse.success(SuccessCode.NHACCOUNT_DEPOSITOR);
    }

    /**
     * 예금주 조회 test API 엔드포인트 - open api 응답 확인용(추후 삭제)
     */
    @GetMapping("/test/depositor")
    public ApplicationResponse<Map<String, Object>> inquireDepositor(@RequestParam String bankCode, @RequestParam String accountNumber) {
        Map<String, Object> response = nhApiService.inquireDepositor(bankCode, accountNumber);
        return ApplicationResponse.success(SuccessCode.NHACCOUNT_DEPOSITOR, response);
    }
}