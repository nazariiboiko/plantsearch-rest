package net.example.plantsearchrest.utils;

import net.example.plantsearchrest.dto.PlantDto;
import net.example.plantsearchrest.dto.PlantListDto;

import java.util.List;

public class PlantListDtoBuilder {

    static public PlantListDto create(List<PlantDto> data, int page, int size) {
        return new PlantListDto().toBuilder()
                .pageSize(size)
                .totalSize(data.size())
                .pageNumber(page)
                .data(makeSinglePage(data, page, size))
                .build();
    }

    static private List<PlantDto> makeSinglePage(List<PlantDto> data, int page, int size) {
        if(data.size() > size * page)
            return data.subList((page - 1) * size, page * size);
        else return data.subList((page - 1) * size, data.size());
    }
}
