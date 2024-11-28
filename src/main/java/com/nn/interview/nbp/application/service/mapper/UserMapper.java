package com.nn.interview.nbp.application.service.mapper;

import com.nn.interview.nbp.adapter.in.dto.UserRequestDto;
import com.nn.interview.nbp.adapter.in.dto.UserResponseDto;
import com.nn.interview.nbp.adapter.out.persistence.entity.UserEntity;
import com.nn.interview.nbp.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = AccountMapper.class)
public interface UserMapper {
    UserMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UserMapper.class);
    User toUser(UserRequestDto userRequestDto);
    UserEntity toUserEntity(User user);
    User toUser(UserEntity userEntity);
    UserRequestDto toUserDto(User user);
    UserResponseDto toUserResponseDto(UserEntity userEntity);

}
