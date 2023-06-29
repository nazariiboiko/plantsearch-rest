package net.example.plantsearchrest.mapper;

import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.entity.PlantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlantMapper {
    PlantMapper INSTANCE = Mappers.getMapper(PlantMapper.class);
    PlantEntity mapDtoToEntity(PlantDto plantDto);
    PlantDto mapEntityToDto(PlantEntity plantEntity);
    void updatePlantEntity(PlantEntity from, @MappingTarget PlantEntity to);

}
