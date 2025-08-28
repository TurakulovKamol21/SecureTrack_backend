package com.company.SecureTrack_backend.service.impl;

import com.company.SecureTrack_backend.service.VirusTotalService;
import com.company.SecureTrack_backend.util.ResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@Component
public class VirusTotalServiceImpl implements VirusTotalService {

    @Value("${virustotal.api.key}")
    private String apiKey;

    private static final String URL_SCAN_ENDPOINT = "https://www.virustotal.com/api/v3/urls";
    private static final String ANALYSIS_ENDPOINT = "https://www.virustotal.com/api/v3/analyses/";
    private static final String APK_INFO_ENDPOINT = "https://www.virustotal.com/api/v3/files/";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    // Yangi: URL -> Base64 id generator
    private String encodeUrl(String url) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(url.getBytes());
    }


    // Yangi scan yuborish
    @Override
    public String submitUrlForScan(String url) throws Exception {
        RequestBody formBody = new FormBody.Builder()
                .add("url", url)
                .build();

        Request request = new Request.Builder()
                .url(URL_SCAN_ENDPOINT)
                .post(formBody)
                .addHeader("x-apikey", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String jsonResponse = response.body().string();
            JsonNode jsonNode = mapper.readTree(jsonResponse);
            return jsonNode.get("data").get("id").asText(); // analysisId
        }
    }

    // Analyses endpoint orqali tekshirish
    @Override
    public ResponseEntity<ResponseDto<String>> checkLink(String analysisId) throws Exception {
        Request request = new Request.Builder()
                .url(ANALYSIS_ENDPOINT + analysisId)
                .get()
                .addHeader("x-apikey", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String jsonResponse = response.body().string();
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode stats = root.path("data").path("attributes").path("stats");

            return ResponseEntity.ok(
                    ResponseDto.<String>builder()
                            .httpStatus(HttpStatus.OK)
                            .data(stats.toString())
                            .build()
            );
        }
    }

    // Fayl (APK hash) ni tekshirish
    @Override
    public ResponseEntity<ResponseDto<String>> checkApk(String apkHash) {
        Request request = new Request.Builder()
                .url(APK_INFO_ENDPOINT + apkHash)
                .get()
                .addHeader("x-apikey", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String jsonResponse = response.body().string();
            return ResponseEntity.ok(
                    ResponseDto.<String>builder()
                            .httpStatus(HttpStatus.OK)
                            .data(jsonResponse)
                            .build()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
