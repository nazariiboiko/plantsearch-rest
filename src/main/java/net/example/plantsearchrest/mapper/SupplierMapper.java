package net.example.plantsearchrest.mapper;

import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.dto.UserDto;
import net.example.plantsearchrest.entity.SupplierEntity;
import net.example.plantsearchrest.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);
    SupplierEntity mapDtoToEntity(SupplierDto dto);
    SupplierDto mapEntityToDto(SupplierEntity entity);

    void updateUserEntity(SupplierEntity from, @MappingTarget SupplierEntity to);
}
