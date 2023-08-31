package net.example.plantsearchrest.mapper;

import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "activateCode", ignore = true)
    UserEntity mapDtoToEntity(UserDto userDto);
    UserDto mapEntityToDto(UserEntity userEntity);
    void updateUserEntity(UserEntity from, @MappingTarget UserEntity to);
    void updateUserEntity(UserDto from, @MappingTarget UserEntity to);
}
