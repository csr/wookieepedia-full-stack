package com.cesaredecal.starwarsapi;

import com.cesaredecal.starwarsapi.models.PeopleResponse;
import reactor.core.publisher.Mono;
import javax.inject.Singleton;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

@Singleton
public class PeopleService implements DataService<PeopleResponse.Person> {

    private static final Logger LOGGER = Logger.getLogger(PeopleService.class.getName());
    private final StarWarsClient starWarsClient;

    @Inject
    public PeopleService(StarWarsClient starWarsClient) {
        this.starWarsClient = starWarsClient;
    }

    @Override
    public Mono<List<PeopleResponse.Person>> fetchAll(int page, List<PeopleResponse.Person> accumulatedResults) {
        LOGGER.log(Level.INFO, "Fetching all people of page: {0}", page);

        return starWarsClient.fetchPeople(page)
                .flatMap(response -> {
                    accumulatedResults.addAll(response.getResults());
                    if (response.getNext() != null) {
                        return fetchAll(page + 1, accumulatedResults);
                    } else {
                        LOGGER.log(Level.INFO, "Fetched people count: {0}", accumulatedResults.size());
                        return Mono.just(accumulatedResults);
                    }
                });
    }

    @Override
    public Comparator<PeopleResponse.Person> getComparator(String sortBy) {
        switch (sortBy) {
            case "created":
                return Comparator.comparing(PeopleResponse.Person::getCreated);
            default:
                return Comparator.comparing(PeopleResponse.Person::getName);
        }
    }
}
