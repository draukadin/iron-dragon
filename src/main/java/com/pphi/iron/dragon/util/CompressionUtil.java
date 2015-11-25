package com.pphi.iron.dragon.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.common.io.Files;

public class CompressionUtil {

    public void compressFile(Path path, Path compressedPath) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(compressedPath.toFile())) {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);
            Files.copy(path.toFile(), gzipOutputStream);
            gzipOutputStream.finish();
            gzipOutputStream.close();
         } finally {
            System.out.println("Deleted uncompressed file: " + path.toFile().delete());
        }
    }

    public InputStream decompressFile(Path path) throws IOException {
        return new GZIPInputStream(new FileInputStream(path.toFile()));
    }
}
