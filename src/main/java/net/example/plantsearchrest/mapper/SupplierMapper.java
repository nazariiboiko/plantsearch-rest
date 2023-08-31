package net.example.plantsearchrest.mapper;

import net.example.plantsearchrest.dto.PlantPreviewDto;
import net.example.plantsearchrest.dto.SupplierDto;
import net.example.plantsearchrest.entity.PlantEntity;
import net.example.plantsearchrest.entity.SupplierEntity;
import net.example.plantsearchrest.model.SinglePage;
import net.example.plantsearchrest.utils.PageUtil;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);
    @Mapping(target = "avaliablePlants",
            source = "avaliablePlants",
            qualifiedByName = "mapListToPagePreviewDto")
    SupplierDto mapEntityToDto(SupplierEntity source, @Context Pageable pageable);

    @Mapping(target = "avaliablePlants", ignore = true)
    SupplierDto mapEntityToDtoIgnorePlants(SupplierEntity source);

    @Mapping(target = "avaliablePlants", ignore = true)
    void updateSupplierEntity(SupplierDto from, @MappingTarget SupplierEntity to);

    @Named("mapListToPagePreviewDto")
    default SinglePage<PlantPreviewDto> mapPageToPreviewDto(List<PlantEntity> data, @Context Pageable pageable) {
        List<PlantPreviewDto> dto = data.stream()
                .map(PlantMapper.INSTANCE::mapEntityToPreviewDto)
                .collect(Collectors.toList());
        return PageUtil.create(dto, pageable.getPageNumber(), pageable.getPageSize());
    }
}
