package com.company.SecureTrack_backend.controller;

import com.company.SecureTrack_backend.service.VirusTotalService;
import com.company.SecureTrack_backend.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/check")
@RestController
public class CheckController {
    private final VirusTotalService virusTotalService;

    @PostMapping("/link")
    public ResponseEntity<ResponseDto<String>> checkLink(@RequestParam String url) throws Exception {
        String analysisId = virusTotalService.submitUrlForScan(url);
        Thread.sleep(5000);
        return virusTotalService.checkLink(analysisId);
    }
    @PostMapping("/apk")
    public ResponseEntity<ResponseDto<String>> checkApk(@RequestParam String apkHash) {
        return virusTotalService.checkApk(apkHash);
    }
}
