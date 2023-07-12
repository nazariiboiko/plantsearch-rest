package net.example.plantsearchrest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PageDto <T> {
    List<T> data;
    int pageNumber;
    int pageSize;
    int totalSize;
}
