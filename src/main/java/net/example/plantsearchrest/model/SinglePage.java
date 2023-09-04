package net.example.plantsearchrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("data")
    private List<T> data;
    @JsonProperty("pageNumber")
    private int pageNumber;
    @JsonProperty("pageSize")
    private int pageSize;
    @JsonProperty("totalSize")
    private int totalSize;
}
