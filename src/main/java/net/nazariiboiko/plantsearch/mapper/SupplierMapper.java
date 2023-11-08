package net.nazariiboiko.plantsearch.mapper;

import net.nazariiboiko.plantsearch.dto.PlantPreviewDto;
import net.nazariiboiko.plantsearch.dto.SupplierDto;
import net.nazariiboiko.plantsearch.dto.UserDto;
import net.nazariiboiko.plantsearch.entity.PlantEntity;
import net.nazariiboiko.plantsearch.entity.SupplierEntity;
import net.nazariiboiko.plantsearch.entity.UserEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    @Mapping(target = "availablePlants", ignore = true)
    SupplierDto mapEntityToDtoIgnorePlants(SupplierEntity source);

    @Mapping(target = "availablePlants",
            source = "availablePlants",
            qualifiedByName = "mapJunctionToPlant")
    SupplierDto mapEntityToDto(SupplierEntity source, @Context Pageable pageable);


    @Named("mapJunctionToPlant")
    default Page<PlantPreviewDto> mapJunctionToPlant(List<PlantEntity> data, @Context Pageable pageable) {
        List<PlantPreviewDto> dto = data.stream()
                .map(PlantMapper.INSTANCE::mapEntityToPreviewDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dto, pageable, data.size());
    }
}
