package net.example.plantsearchrest.mapper;

import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.entity.SupplierEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);
    SupplierEntity mapDtoToEntity(SupplierDto dto);
    SupplierDto mapEntityToDto(SupplierEntity entity);

    void updateSupplierEntity(SupplierDto from, @MappingTarget SupplierEntity to);
}
