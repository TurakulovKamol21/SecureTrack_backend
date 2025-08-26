package com.company.SecureTrack_backend.dto;

import com.company.SecureTrack_backend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String picture;
    private List<Role> roles;


}
