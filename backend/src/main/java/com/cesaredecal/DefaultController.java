package com.cesaredecal;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;
import java.io.IOException;

import io.micronaut.http.annotation.QueryValue;
import io.micronaut.runtime.event.annotation.EventListener;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;


@Controller("/api/v1")
public class DefaultController {

    private static final Logger LOGGER = Logger.getLogger(DefaultController.class.getName());

    @Inject
    StarWarsService starWarsService;

    @Inject
    private JsonFileService jsonFileService;

    public DefaultController(StarWarsService starWarsService) {
        this.starWarsService = starWarsService;
    }

    @Get(produces = MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World!";
    }

    @Get(value = "/people/columns", produces = MediaType.APPLICATION_JSON)
    public String getPeopleTableColumns() throws IOException {
        // TODO: this should go inside the service
        return jsonFileService.readJsonFile("people_metadata.json");
    }

    @Get(value = "/people/data", produces = MediaType.APPLICATION_JSON)
    public Mono<String> getPeople(@Nullable @QueryValue String sortBy, @Nullable @QueryValue String sortOrder) throws IOException {
        return starWarsService.fetchAllPeopleFromStorage(sortBy, sortOrder);
    }

    @Get(value = "/planets/columns", produces = MediaType.APPLICATION_JSON)
    public String getPlanetsTableColumns() throws IOException {
        return jsonFileService.readJsonFile("planets_metadata.json");
    }

    @EventListener
    void init(StartupEvent event) {
        // The application fetches all the data from the Star Wars API at startup time and saves it to the disk
        starWarsService.fetchAllPeopleAndWriteToJson();
    }
}
