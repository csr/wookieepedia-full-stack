package com.cesaredecal;

import com.cesaredecal.starwarsapi.StarWarsService;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

import javax.inject.Inject;
import java.io.IOException;

import reactor.core.publisher.Mono;

import com.cesaredecal.starwarsapi.models.DataType;

@Controller("/api/v1")
public class StarWarsController {

    private final StarWarsService starWarsService;

    @Inject
    public StarWarsController(StarWarsService starWarsService) {
        this.starWarsService = starWarsService;
    }

    // People endpoints

    @Get(value = "/people/columns", produces = MediaType.APPLICATION_JSON)
    public Mono<String> getPeopleTableColumns() {
        return starWarsService.fetchColumns(DataType.PEOPLE);
    }

    @Get(value = "/people/data", produces = MediaType.APPLICATION_JSON)
    public Mono<String> getPeople(@Nullable @QueryValue String sortBy, @Nullable @QueryValue String sortOrder) {
        return starWarsService.fetchTableDataFromStorage(DataType.PEOPLE, sortBy, sortOrder);
    }

    // Planets endpoints

    @Get(value = "/planets/columns", produces = MediaType.APPLICATION_JSON)
    public Mono<String> getPlanetsTableColumns() {
        return starWarsService.fetchColumns(DataType.PLANETS);
    }

    @Get(value = "/planets/data", produces = MediaType.APPLICATION_JSON)
    public Mono<String> getPlanetsTableData(@Nullable @QueryValue String sortBy, @Nullable @QueryValue String sortOrder) {
        return starWarsService.fetchTableDataFromStorage(DataType.PLANETS, sortBy, sortOrder);
    }
}
