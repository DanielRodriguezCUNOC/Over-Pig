package com.piglatin.common.infrastructure.adapters;

import com.piglatin.common.application.ports.output.SourceCodeProvider;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Read file .pig, .y and .z
 * Resolve imports for PigLatin language
 */
@Getter
@Setter
public class FileSourceCodeProvider implements SourceCodeProvider {

    @Override
    public String readSource(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) throw new RuntimeException("File not found" +  filePath);
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Error to read file: " + filePath, e);
        }
    }

    @Override
    public boolean exists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    @Override
    public String resolvePath(String projectRoot, String importPath) {
        String normalized = importPath.replace(".", "/");
        return Paths.get(projectRoot, normalized).toAbsolutePath().toString();
    }
}
