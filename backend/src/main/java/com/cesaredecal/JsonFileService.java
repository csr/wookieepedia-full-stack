package com.cesaredecal;

import com.cesaredecal.models.PeopleResponse;
import io.micronaut.serde.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class JsonFileService {

    @Inject
    ObjectMapper objectMapper;

    private static final Logger LOGGER = Logger.getLogger(JsonFileService.class.getName());

    public String readJsonFile(String fileName) throws IOException {
        try {
            // Assume the file is located in the root folder of the project
            byte[] bytes = Files.readAllBytes(Paths.get(fileName));
            String json = new String(bytes, StandardCharsets.UTF_8);
            LOGGER.log(Level.INFO, "Read JSON: {0}", json);
            return json;
        } catch (IOException e) {
            throw new IOException("Failed to read JSON file", e);
        }
    }

    public <T> String writeToJsonFile(T object, String fileName) {
        try {
            // Convert Java object to JSON string
            String json = objectMapper.writeValueAsString(object);
            LOGGER.log(Level.INFO, "Writing a new file with name: {0}", fileName);

            File file = new File(fileName);
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(json);
            }

            return json;
        } catch (Exception e) {
            throw new RuntimeException("Failed to write object to JSON file", e);
        }
    }
}
