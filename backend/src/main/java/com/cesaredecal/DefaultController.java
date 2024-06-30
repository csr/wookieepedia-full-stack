package com.cesaredecal;

import com.cesaredecal.models.StarWarsResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;
import java.io.IOException;
import reactor.core.publisher.Mono;

@Controller("/api/v1")
public class DefaultController {

    private final StarWarsClient starWarsClient;

    @Inject
    private JsonFileService jsonFileService;

    public DefaultController(StarWarsClient starWarsClient) {
        this.starWarsClient = starWarsClient;
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
    public String getPeopleTableData() throws IOException {
        return jsonFileService.readJsonFile("people_data.json");
    }

    @Get(value = "/planets/columns", produces = MediaType.APPLICATION_JSON)
    public String getPlanetsTableColumns() throws IOException {
        return jsonFileService.readJsonFile("planets_metadata.json");
    }

    @Get("/people")
    public Mono<String> getPeople() {
        return starWarsClient.fetchPeople();
    }
}
