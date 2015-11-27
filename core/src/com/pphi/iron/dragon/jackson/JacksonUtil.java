package com.pphi.iron.dragon.jackson;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.pphi.iron.dragon.util.CompressionUtil;

public final class JacksonUtil {

    private static final Path TEMP = Paths.get("GameData/temp.json");
    private static final CompressionUtil COMPRESSION_UTIL = new CompressionUtil();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JacksonUtil() {
        OBJECT_MAPPER.registerModule(new IronDragonModule());
        OBJECT_MAPPER.registerModule(new GuavaModule());
    }

    public static void serializeToFile(Object object, Path path) throws IOException {
        ObjectWriter objectWriter = OBJECT_MAPPER.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(TEMP.toFile(), object);
        COMPRESSION_UTIL.compressFile(TEMP, path);
    }

    public static <T> T deserialize(Path path, Class<T> aClass) throws IOException {
        try {
            return OBJECT_MAPPER.readValue(COMPRESSION_UTIL.decompressFile(path), aClass);
        } catch (ZipException ex) {
            return OBJECT_MAPPER.readValue(path.toFile(), aClass);
        }
    }
}
