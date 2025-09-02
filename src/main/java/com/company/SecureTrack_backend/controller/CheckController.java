package com.company.SecureTrack_backend.controller;

import com.company.SecureTrack_backend.dto.LinkAnalysesResultDto;
import com.company.SecureTrack_backend.model.LinkAnalysesResult;
import com.company.SecureTrack_backend.repository.LinkAnalyzeResultRepository;
import com.company.SecureTrack_backend.service.VirusTotalService;
import com.company.SecureTrack_backend.service.mapper.LinkAnalyzeResultMapper;
import com.company.SecureTrack_backend.util.HashUtil;
import com.company.SecureTrack_backend.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/check")
@RestController
public class CheckController {
    private final VirusTotalService virusTotalService;
    private final LinkAnalyzeResultRepository linkAnalyzeResultRepository;
    private final LinkAnalyzeResultMapper linkAnalyzeResultMapper;
    private final HashUtil hashUtil;

    @PostMapping("/link")
    public ResponseEntity<ResponseDto<LinkAnalysesResultDto>> checkLink(@RequestParam String url) throws Exception {
        Optional<LinkAnalysesResult> byLink = linkAnalyzeResultRepository.findByLink(url);
        if (byLink.isPresent()) {
            return ResponseEntity.ok(
                    ResponseDto.<LinkAnalysesResultDto>builder()
                            .message("Result found from db")
                            .data(linkAnalyzeResultMapper.toDto(byLink.get()))
                            .httpStatus(HttpStatus.OK)
                            .build()
            );
        }
        String analysisId = virusTotalService.submitUrlForScan(url);
        Thread.sleep(5000);
        return virusTotalService.checkLink(analysisId,url);
    }

    @PostMapping("/apk")
    public ResponseEntity<ResponseDto<String>> checkApk(@RequestParam String apkHash) {
        return virusTotalService.checkApk(apkHash);
    }

    @GetMapping("/apk/hash")
    public ResponseEntity<ResponseDto<String>> getApkHash(@RequestParam String filePath) {
        try {
            return ResponseEntity.ok(ResponseDto.<String>builder()
                    .httpStatus(HttpStatus.OK)
                    .data(HashUtil.sha256OfFile(filePath))
                    .message("Apk hash found")
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
