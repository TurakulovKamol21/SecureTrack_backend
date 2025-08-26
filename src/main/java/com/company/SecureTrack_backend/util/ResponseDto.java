package com.company.SecureTrack_backend.util;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class ResponseDto<T> {
    private String message;
    private T data;
    private HttpStatus httpStatus;
}
