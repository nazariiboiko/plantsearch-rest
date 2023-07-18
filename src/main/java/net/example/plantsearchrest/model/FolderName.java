package net.example.plantsearchrest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FolderName {
    IMAGE("images"),
    SKETCH("sketches"),
    TEST("test");

    private final String value;

}
