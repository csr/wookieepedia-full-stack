package com.cesaredecal;

import com.cesaredecal.models.PeopleResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

@Client("https://swapi.dev/api")
public interface StarWarsClient {

    @Get("/people{?page}")
    Mono<PeopleResponse> fetchPeople(@QueryValue Integer page);
}
