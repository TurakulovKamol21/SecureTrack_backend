package com.company.SecureTrack_backend.service.impl;

import com.company.SecureTrack_backend.dto.UserDto;
import com.company.SecureTrack_backend.model.User;
import com.company.SecureTrack_backend.repository.UserRepository;
import com.company.SecureTrack_backend.service.UserService;
import com.company.SecureTrack_backend.service.mapper.UserMapper;
import com.company.SecureTrack_backend.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<ResponseDto<UserDto>> getUserById(UUID id) {
        User user = this.userRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(
                ResponseDto.<UserDto>builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Success")
                        .data(this.userMapper.toDto(user))
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDto<List<UserDto>>> getAllUsers() {
        return ResponseEntity.ok(ResponseDto.<List<UserDto>>builder()
                .data(userRepository.findAllByDeletedAtIsNull()
                        .stream()
                        .map(userMapper::toDto)
                        .collect(Collectors.toList()))
                .httpStatus(HttpStatus.OK)
                .message("USERS:")
                .build());
    }

    @Override
    public ResponseEntity<ResponseDto<UserDto>> updateUser(UserDto userDto) {
        userRepository.findByIdAndDeletedAtIsNull(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        userDto.setUpdatedAt(LocalDateTime.now());

        return ResponseEntity.ok(ResponseDto.<UserDto>builder()
                .httpStatus(HttpStatus.OK)
                .message("User successfully updated")
                .data(userMapper.toDto(userRepository.save(userMapper.toUser(userDto))))
                .build());
    }

}
