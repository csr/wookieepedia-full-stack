package com.cesaredecal;

import com.cesaredecal.models.PeopleResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;
import java.io.IOException;

import java.util.List;

import io.micronaut.serde.ObjectMapper;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller("/api/v1")
public class DefaultController {

    private static final Logger LOGGER = Logger.getLogger(DefaultController.class.getName());

    @Inject
    StarWarsService starWarsService;

    @Inject
    private JsonFileService jsonFileService;

    @Inject
    ObjectMapper objectMapper;

    public DefaultController(StarWarsService starWarsService) {
        this.starWarsService = starWarsService;
    }

    @Get(produces = MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World!";
    }

    @Get(value = "/people/columns", produces = MediaType.APPLICATION_JSON)
    public String getPeopleTableColumns() throws IOException {
        return jsonFileService.readJsonFile("people_metadata.json");
    }

    @Get(value = "/people/data", produces = MediaType.APPLICATION_JSON)
    public Mono<String> getPeople() {
        return starWarsService.fetchAllPeople().map(this::writeListToJsonFile);
    }

    @Get(value = "/planets/columns", produces = MediaType.APPLICATION_JSON)
    public String getPlanetsTableColumns() throws IOException {
        return jsonFileService.readJsonFile("planets_metadata.json");
    }

    private String writeListToJsonFile(List<PeopleResponse.Person> people) {
        try {
            // covert Java object to JSON strings
            String json = objectMapper.writeValueAsString(people);
            LOGGER.log(Level.INFO, "JSON: {0}", json);
            return json;
        } catch (Exception e) {
            throw new RuntimeException("Failed to write list to JSON file", e);
        }
    }
}
