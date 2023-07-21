package net.example.plantsearchrest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SinglePage<T> {
    private List<T> data;
    private int pageNumber;
    private int pageSize;
    private int totalSize;
}
