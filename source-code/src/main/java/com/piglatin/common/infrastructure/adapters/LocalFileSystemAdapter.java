package com.piglatin.common.infrastructure.adapters;

import com.piglatin.common.application.dto.LanguageType;
import com.piglatin.common.application.dto.NodeType;
import com.piglatin.common.application.dto.ProjectNodeDTO;
import com.piglatin.common.application.ports.output.FileSystemPort;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Implements tree work flow
 */
public class LocalFileSystemAdapter implements FileSystemPort {

    @Override
    public ProjectNodeDTO readDirectory(String path) {
        Path root = Paths.get(path);
        if (!Files.exists(root) || !Files.isDirectory(root)) {
            throw new RuntimeException("Ruta no válida o no es directorio: " + path);
        }
        return buildNode(root);
    }

    @Override
    public void writeFile(String path, String content) {
        try {
            Path filePath = Paths.get(path);
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, content);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir archivo: " + path, e);
        }
    }

    @Override
    public String readFile(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Error al leer archivo: " + path, e);
        }
    }

    @Override
    public void createDirectory(String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Error al crear directorio: " + path, e);
        }
    }

    @Override
    public void delete(String path) {
        try {
            Path target = Paths.get(path);
            if (Files.isDirectory(target)) {
                Files.walkFileTree(target, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                Files.deleteIfExists(target);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar: " + path, e);
        }
    }

    @Override
    public byte[] compressDirectory(String rootPath) {
        Path root = Paths.get(rootPath);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {

            Files.walk(root).forEach(path -> {
                String zipEntryName = root.relativize(path).toString();
                try {
                    if (Files.isDirectory(path)) {
                        zos.putNextEntry(new ZipEntry(zipEntryName + "/"));
                        zos.closeEntry();
                    } else {
                        zos.putNextEntry(new ZipEntry(zipEntryName));
                        zos.write(Files.readAllBytes(path));
                        zos.closeEntry();
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error al comprimir: " + path, e);
                }
            });
            zos.finish();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error al comprimir directorio: " + rootPath, e);
        }
    }

    @Override
    public boolean exists(String path) {
        return Files.exists(Paths.get(path));
    }

    private ProjectNodeDTO buildNode(Path path) {
        String name = path.getFileName() != null ? path.getFileName().toString() : path.toString();
        boolean isDirectory = Files.isDirectory(path);

        ProjectNodeDTO node = new ProjectNodeDTO();
        node.setName(name);
        node.setPath(path.toAbsolutePath().toString());
        node.setNodeType(isDirectory ? NodeType.DIRECTORY : NodeType.FILE);
        node.setLanguageType(detectLanguage(name));

        if (!isDirectory) {
            try {
                node.setContent(Files.readString(path));
            } catch (IOException e) {
                node.setContent("");
            }
        }

        if (isDirectory) {
            try (var stream = Files.list(path)) {
                stream.forEach(child -> node.addChild(buildNode(child)));
            } catch (IOException e) {
                throw new RuntimeException("Error trying build node project: " +  path, e);
            }
        }

        return node;
    }

    private LanguageType detectLanguage(String fileName) {
        if (fileName.endsWith(".pig")) return LanguageType.PIGLATIN;
        if (fileName.endsWith(".y")) return LanguageType.Y;
        if (fileName.endsWith(".z")) return LanguageType.ZETARIANO;
        return null;
    }
}
