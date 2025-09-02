package com.company.SecureTrack_backend.dto;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;



/**
 * DTO for {@link com.company.SecureTrack_backend.model.LinkAnalysesResult}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class LinkAnalysesResultDto {

    @Column(unique = true, nullable = false)
    private String link;
    private int malicious;
    private int suspicious;
    private int undetected;
    private int harmless;
    private int timeout;
}