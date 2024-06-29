package com.cesaredecal;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class JsonFileService {

    private static final Logger LOGGER = Logger.getLogger(JsonFileService.class.getName());

    public String readJsonFile(String fileName) throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new IOException("File not found: " + fileName);
            }
            byte[] bytes = is.readAllBytes();
            String json = new String(bytes, StandardCharsets.UTF_8);
            LOGGER.log(Level.INFO, "Read JSON: {0}", json);
            return json;
        }
    }
}
