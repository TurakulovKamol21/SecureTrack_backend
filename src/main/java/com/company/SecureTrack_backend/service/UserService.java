package com.company.SecureTrack_backend.service;

import com.company.SecureTrack_backend.dto.UserDto;
import com.company.SecureTrack_backend.util.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    ResponseEntity<ResponseDto<UserDto>> getUserById(UUID id);

    ResponseEntity<ResponseDto<List<UserDto>>> getAllUsers();

    ResponseEntity<ResponseDto<UserDto>> updateUser(UserDto userDto);
}
