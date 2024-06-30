package com.cesaredecal;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

@Client("https://swapi.dev/api")
public interface StarWarsClient {

    @Get("/people")
    Mono<String> fetchPeople();
}
