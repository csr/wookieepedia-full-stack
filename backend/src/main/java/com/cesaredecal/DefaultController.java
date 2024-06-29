package com.cesaredecal;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;
import java.io.IOException;

@Controller("/api/v1")
public class DefaultController {

    @Inject
    private JsonFileService jsonFileService;

    @Get(produces = MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World!";
    }

    @Get(value = "/people/columns", produces = MediaType.APPLICATION_JSON)
    public String getPeopleTableColumns() throws IOException {
        return jsonFileService.readJsonFile("people_metadata.json");
    }

    @Get(value = "/planets/columns", produces = MediaType.APPLICATION_JSON)
    public String getPlanetsTableColumns() throws IOException {
        return jsonFileService.readJsonFile("planets_metadata.json");
    }
}
