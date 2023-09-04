package net.example.plantsearchrest.utils;

import net.example.plantsearchrest.model.SinglePage;

import java.util.List;
import java.util.stream.Collectors;

public class PageUtil {
    /**
     * Creates a paginated response with the provided data.
     *
     * @param data       The list of data to be paginated.
     * @param pageNumber The current page number (1-based index).
     * @param pageSize   The number of items per page.
     * @return A SinglePage object representing the paginated response.
     */
    static public SinglePage create(List data, int pageNumber, int pageSize) {
            if(pageNumber <= 0)
            pageNumber = 1;
        if(pageSize <= 0)
            pageSize = 1;

        List sortedData = (List) data.stream().sorted().collect(Collectors.toList());
        return new SinglePage().toBuilder()
                .pageSize(pageSize)
                .totalSize(data.size())
                .pageNumber(pageNumber)
                .data(makeSinglePageFromList(sortedData, pageNumber, pageSize))
                .build();
    }

    /**
     * Creates a sublist of data for the current page.
     *
     * @param data The sorted list of data.
     * @param page The current page number (1-based index).
     * @param size The number of items per page.
     * @return A sublist of data representing the items for the current page.
     */
    static private List makeSinglePageFromList(List data, int page, int size) {
        // Calculate the starting and ending indices for the sublist
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(page * size, data.size());

        // Return a sublist of data for the current page
        return data.subList(startIndex, endIndex);
    }
}
