package com.cesaredecal;

import com.cesaredecal.models.PeopleResponse;
import com.cesaredecal.models.PeopleResponse.Person;
import reactor.core.publisher.Mono;

import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class StarWarsService {

    private static final Logger LOGGER = Logger.getLogger(StarWarsService.class.getName());

    private final StarWarsClient starWarsClient;

    public StarWarsService(StarWarsClient starWarsClient) {
        this.starWarsClient = starWarsClient;
    }

    private Mono<List<PeopleResponse.Person>> fetchAllPeople(int page, List<PeopleResponse.Person> accumulatedResults) {
        LOGGER.log(Level.INFO, "Fetching all people of page: {0}", page);

        return starWarsClient.fetchPeople(page)
                .flatMap(response -> {
                    accumulatedResults.addAll(response.getResults());
                    if (response.getNext() != null) {
                        return fetchAllPeople(page + 1, accumulatedResults);
                    } else {
                        return Mono.just(accumulatedResults);
                    }
                });
    }

    public Mono<List<Person>> fetchAllPeople() {
        return fetchAllPeople(1, new ArrayList<>());
    }
}
