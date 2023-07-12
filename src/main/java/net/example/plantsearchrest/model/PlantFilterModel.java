package net.example.plantsearchrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlantFilterModel {
    @JsonProperty("params")
    private PlantFilterDataModel data;

    @Override
    public String toString() {
        return data.toString();
    }
}
