package com.company.SecureTrack_backend.repository;

import com.company.SecureTrack_backend.model.LinkAnalysesResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkAnalyzeResultRepository extends JpaRepository<LinkAnalysesResult,String> {

    Optional<LinkAnalysesResult> findByLink(String link);
}
