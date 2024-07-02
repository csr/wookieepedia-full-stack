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

    public Mono<String> fetchAllPeopleFromStorage(String searchTerm) throws IOException {
        String fileName = "people_data.json";
        String jsonContent = jsonFileService.readJsonFile(fileName);

        // There is nothing to filter/search, returning
        if (searchTerm == null || searchTerm.isEmpty()) {
            return Mono.just(jsonContent);
        }

        List<PeopleResponse.Person> people = objectMapper.readValue(jsonContent, Argument.listOf(PeopleResponse.Person.class));

        List<PeopleResponse.Person> filteredPeople = people.stream()
                .filter(person -> person.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());

        return Mono.just(objectMapper.writeValueAsString(filteredPeople));
    }

    public void fetchAllPeopleAndWriteToJson() {
        fetchAllPeople(1, new ArrayList<>())
                .subscribe(results -> {
                    jsonFileService.writeToJsonFile(results, "people_data.json");
                });
    }
}
