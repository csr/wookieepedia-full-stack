package com.cesaredecal;

import com.cesaredecal.models.PeopleResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.ObjectMapper;
import reactor.core.publisher.Mono;

import jakarta.inject.Singleton;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Singleton
public class StarWarsService {

    private static final Logger LOGGER = Logger.getLogger(StarWarsService.class.getName());

    @Inject
    private JsonFileService jsonFileService;

    @Inject
    ObjectMapper objectMapper;

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

    public Mono<String> fetchAllPeopleFromStorage(String sortBy, String sortOrder) throws IOException {
        String fileName = "people_data.json";
        String jsonContent = jsonFileService.readJsonFile(fileName);

        // No sorting needed here
        if ((sortBy == null || sortBy.isEmpty()) || (sortOrder == null || sortOrder.isEmpty())) {
            LOGGER.log(Level.INFO, "No sorting needed here");
            LOGGER.log(Level.INFO, "sortBy: {0}", sortBy);
            LOGGER.log(Level.INFO, "sortOrder: {0}", sortOrder);
            return Mono.just(jsonContent);
        }

        List<PeopleResponse.Person> people = objectMapper.readValue(jsonContent, Argument.listOf(PeopleResponse.Person.class));
        Comparator<PeopleResponse.Person> comparator;

        switch (sortBy) {
            case "created":
                comparator = Comparator.comparing(PeopleResponse.Person::getCreated);
                break;
            default:
                comparator = Comparator.comparing(PeopleResponse.Person::getName);
        }

        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        Collections.sort(people, comparator);

        return Mono.just(objectMapper.writeValueAsString(people));
    }

    public void fetchAllPeopleAndWriteToJson() {
        fetchAllPeople(1, new ArrayList<>())
                .subscribe(results -> {
                    jsonFileService.writeToJsonFile(results, "people_data.json");
                });
    }
}
