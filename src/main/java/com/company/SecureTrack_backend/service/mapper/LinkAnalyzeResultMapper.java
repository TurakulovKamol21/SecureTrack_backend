package com.company.SecureTrack_backend.service.mapper;

import com.company.SecureTrack_backend.dto.LinkAnalysesResultDto;
import com.company.SecureTrack_backend.model.LinkAnalysesResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LinkAnalyzeResultMapper {

    LinkAnalysesResult toModel(LinkAnalysesResultDto dto);

    LinkAnalysesResultDto toDto(LinkAnalysesResult entity);
}
