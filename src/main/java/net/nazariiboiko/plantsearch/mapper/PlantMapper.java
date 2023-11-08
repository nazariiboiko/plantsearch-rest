package net.nazariiboiko.plantsearch.mapper;
import net.nazariiboiko.plantsearch.dto.PlantDto;
import net.nazariiboiko.plantsearch.dto.PlantPreviewDto;
import net.nazariiboiko.plantsearch.entity.PlantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlantMapper {
    PlantMapper INSTANCE = Mappers.getMapper(PlantMapper.class);

    PlantEntity mapDtoToEntity(PlantDto plantDto);

    PlantDto mapEntityToDto(PlantEntity plantEntity);

    PlantPreviewDto mapEntityToPreviewDto(PlantEntity plantEntity);

    void updatePlantEntity(PlantDto plantDto, @MappingTarget PlantEntity plantEntity);

}