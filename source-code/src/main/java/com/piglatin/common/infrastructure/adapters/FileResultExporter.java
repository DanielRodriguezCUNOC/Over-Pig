package com.piglatin.common.infrastructure.adapters;

import com.piglatin.common.application.ports.output.ResultExporter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public class FileResultExporter implements ResultExporter {

    public static final String RUNTIME_FILE_NAME = "runtime.asm";
    public static final String RUNTIME_RESOURCE = "/riscv/runtime.asm";

    private String outputDirectory;

    public FileResultExporter(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    @Override
    public void setOutputDirectory(String directory) {
        this.outputDirectory = directory;
        if (directory != null && !directory.isBlank()) {
            try {
                Path dir = Paths.get(directory);
                if (!Files.exists(dir)) {
                    Files.createDirectories(dir);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al crear directorio: " + directory, e);
            }
        }
    }

    @Override
    public String export3DC(String code, String fileName) {
        String fullName = fileName + ".3d";
        return writeFile(fullName, code);
    }

    @Override
    public String exportAsm(String code, String fileName) {
        String fullName = fileName + ".asm";
        return writeFile(fullName, code);
    }

    @Override
    public String exportRuntime() {
        try (InputStream in = FileResultExporter.class.getResourceAsStream(RUNTIME_RESOURCE)) {
            Path dir = Paths.get(outputDirectory);
            if (!Files.exists(dir)) Files.createDirectories(dir);
            Path target = dir.resolve(RUNTIME_FILE_NAME);

            if (in != null) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                return target.toAbsolutePath().toString();
            }
            return Files.exists(target) ? target.toAbsolutePath().toString() : null;
        } catch (IOException e) {
            throw new RuntimeException("Error al exportar " + RUNTIME_FILE_NAME, e);
        }
    }

    private String writeFile(String fileName, String content) {
        try {
            Path dir = Paths.get(outputDirectory);
            if (!Files.exists(dir)) Files.createDirectories(dir);
            Path filePath = dir.resolve(fileName);
            Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Error al exportar archivo: " + fileName, e);
        }
    }
}
