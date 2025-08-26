package com.company.SecureTrack_backend.controller;

import com.company.SecureTrack_backend.dto.UserDto;
import com.company.SecureTrack_backend.model.User;
import com.company.SecureTrack_backend.repository.UserRepository;
import com.company.SecureTrack_backend.service.UserService;
import com.company.SecureTrack_backend.util.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResponseDto<UserDto>>getUserById(@RequestParam("id") UUID id){
        return userService.getUserById(id);
    }
    @GetMapping("/get-all")
    public ResponseEntity<ResponseDto<List<UserDto>>> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping
    public ResponseEntity<ResponseDto<UserDto>> updateUser(@Valid @RequestBody UserDto userDto){
        return userService.updateUser(userDto);
    }

}
