package net.example.plantsearchrest.utils;

import net.example.plantsearchrest.dto.PageDto;
import net.example.plantsearchrest.dto.PlantDto;

import java.util.List;

public class PageUtil {

    static public PageDto create(List data, int pageNumber, int pageSize) {
        return new PageDto().toBuilder()
                .pageSize(pageSize)
                .totalSize(data.size())
                .pageNumber(pageNumber)
                .data(makeSinglePageFromList(data, pageNumber, pageSize))
                .build();
    }

    static private List makeSinglePageFromList(List data, int page, int size) {
        if(data.size() > size * page)
            return data.subList((page - 1) * size, page * size);
        else return data.subList((page - 1) * size, data.size());
    }
}
