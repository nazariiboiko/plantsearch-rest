package net.nazariiboiko.plantsearch.mapper;

import net.nazariiboiko.plantsearch.dto.UserDto;
import net.nazariiboiko.plantsearch.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity mapDtoToEntity(UserDto userDto);

    UserDto mapEntityToDto(UserEntity userEntity);
}