package com.company.SecureTrack_backend.service.mapper;

import com.company.SecureTrack_backend.dto.UserDto;
import com.company.SecureTrack_backend.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
    User toUser(UserDto userDto);

    List<UserDto>toDtoList(List<User> users);
    List<User>toUserList(List<UserDto> userDtos);
}
