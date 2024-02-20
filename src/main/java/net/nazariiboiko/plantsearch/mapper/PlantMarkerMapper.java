package net.nazariiboiko.plantsearch.mapper;

import net.nazariiboiko.plantsearch.dto.PlantMarkerDto;
import net.nazariiboiko.plantsearch.dto.PlantMarkerGroupDto;
import net.nazariiboiko.plantsearch.entity.PlantMarkerEntity;
import net.nazariiboiko.plantsearch.entity.PlantMarkerGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PlantMarkerMapper {
    PlantMarkerMapper INSTANCE = Mappers.getMapper(PlantMarkerMapper.class);

    PlantMarkerEntity mapDtoToEntity(PlantMarkerDto plantMarkerDto);
    PlantMarkerDto mapEntityToDto(PlantMarkerEntity plantMarkerEntity);

    PlantMarkerGroupEntity mapGroupDtoToEntity(PlantMarkerGroupDto plantMarkerGroupDto);
    @Mapping(target = "markers", ignore = true)
    PlantMarkerGroupEntity mapGroupDtoToEntityIgnoreMarkers(PlantMarkerGroupDto plantMarkerDto);


    @Mapping(target = "markers", ignore = true)
    PlantMarkerGroupDto mapGroupEntityToDtoIgnoreMarkers(PlantMarkerGroupEntity entity);

    @Mapping(target = "markers", source = "markers", qualifiedByName = "mapListMarkers")
    PlantMarkerGroupDto mapGroupEntityToDto(PlantMarkerGroupEntity plantMarkerGroupEntity);

    @Named("mapListMarkers")
    default List<PlantMarkerDto> mapListMarkers(List<PlantMarkerEntity> markerEntities) {
        return markerEntities.stream()
                .map(INSTANCE::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
