package com.cesaredecal.starwarsapi;

import com.cesaredecal.starwarsapi.models.PlanetsResponse;
import reactor.core.publisher.Mono;
import javax.inject.Singleton;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

@Singleton
public class PlanetsService implements DataService<PlanetsResponse.Planet> {

    private static final Logger LOGGER = Logger.getLogger(PlanetsService.class.getName());
    private final StarWarsClient starWarsClient;

    @Inject
    public PlanetsService(StarWarsClient starWarsClient) {
        this.starWarsClient = starWarsClient;
    }

    @Override
    public Mono<List<PlanetsResponse.Planet>> fetchAll(int page, List<PlanetsResponse.Planet> accumulatedResults) {
        LOGGER.log(Level.INFO, "Fetching all planets of page: {0}", page);

        return starWarsClient.fetchPlanets(page)
                .flatMap(response -> {
                    accumulatedResults.addAll(response.getResults());
                    if (response.getNext() != null) {
                        return fetchAll(page + 1, accumulatedResults);
                    } else {
                        LOGGER.log(Level.INFO, "Fetched planets count: {0}", accumulatedResults.size());
                        return Mono.just(accumulatedResults);
                    }
                });
    }

    @Override
    public Comparator<PlanetsResponse.Planet> getComparator(String sortBy) {
        switch (sortBy) {
            case "created":
                return Comparator.comparing(PlanetsResponse.Planet::getCreated);
            default:
                return Comparator.comparing(PlanetsResponse.Planet::getName);
        }
    }
}
