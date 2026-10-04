package com.piglatin.common.infrastructure.adapters;

import com.piglatin.common.application.ports.output.ResultExporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileResultExporter implements ResultExporter {

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
    public String exportC(String code, String fileName) {
        String fullName = fileName + ".c";
        return writeFile(fullName, code);
    }

    @Override
    public String exportCombined(String c3code, String cCode, String fileName) {
        String combined = "/* --- Tree Directions Code --- */\n\n" +
                c3code + "\n\n" + "/* ### C Code ### */\n\n" + cCode;
        String fullName = fileName + "_combined.txt";
        return writeFile(fullName, combined);
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
