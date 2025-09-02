package com.company.SecureTrack_backend.service;

import com.company.SecureTrack_backend.dto.LinkAnalysesResultDto;
import com.company.SecureTrack_backend.util.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface VirusTotalService {
    ResponseEntity<ResponseDto<LinkAnalysesResultDto>> checkLink(String analyseId,String link) throws Exception;

    ResponseEntity<ResponseDto<String>> checkApk(String apkHash);

    String submitUrlForScan(String url) throws Exception;
}
