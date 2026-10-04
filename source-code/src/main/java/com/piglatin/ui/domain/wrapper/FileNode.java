package com.piglatin.ui.domain.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
public class FileNode {

    private final File file;

    @Override
    public String toString() {
        String name = file.getName();
        return name.isEmpty() ? file.getAbsolutePath() : name;
    }

}
