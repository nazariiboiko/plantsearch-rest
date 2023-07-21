package net.example.plantsearchrest.utils;

import net.example.plantsearchrest.model.SinglePage;

import java.util.List;
import java.util.stream.Collectors;

public class PageUtil {

    static public SinglePage create(List data, int pageNumber, int pageSize) {
        List sortedData = (List) data.stream().sorted().collect(Collectors.toList());
        return new SinglePage().toBuilder()
                .pageSize(pageSize)
                .totalSize(data.size())
                .pageNumber(pageNumber)
                .data(makeSinglePageFromList(sortedData, pageNumber, pageSize))
                .build();
    }

    static private List makeSinglePageFromList(List data, int page, int size) {
        if(data.size() > size * page)
            return data.subList((page - 1) * size, page * size);
        else return data.subList((page - 1) * size, data.size());
    }
}
