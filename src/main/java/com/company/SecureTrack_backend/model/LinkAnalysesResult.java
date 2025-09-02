package com.company.SecureTrack_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "analyze_result")
@Entity
public class LinkAnalysesResult {
    @Id
    @Column(unique = true, nullable = false)
    private String link;
    private int malicious;
    private int suspicious;
    private int undetected;
    private int harmless;
    private int timeout;
}
