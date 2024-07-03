package com.cesaredecal;

import com.cesaredecal.models.DataType;
import com.cesaredecal.models.PeopleResponse;
import com.cesaredecal.models.PlanetsResponse;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.core.type.Argument;
import io.micronaut.runtime.event.annotation.EventListener;
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

@Singleton
public class StarWarsService {

    private static final Logger LOGGER = Logger.getLogger(StarWarsService.class.getName());

    private final StarWarsClient starWarsClient;
    private final JsonFileService jsonFileService;
    private final ObjectMapper objectMapper;

    @Inject
    public StarWarsService(StarWarsClient starWarsClient, JsonFileService jsonFileService, ObjectMapper objectMapper) {
        this.starWarsClient = starWarsClient;
        this.jsonFileService = jsonFileService;
        this.objectMapper = objectMapper;
    }

    // Lifecycle events

    @EventListener
    void init(StartupEvent event) {
        // The application fetches all the data from the Star Wars API at startup time and saves it to the disk
        fetchTableDataAndSaveJson(DataType.PEOPLE);
        fetchTableDataAndSaveJson(DataType.PLANETS);
    }

    // Write operations

    private Mono<List<PeopleResponse.Person>> fetchAllPeople(int page, List<PeopleResponse.Person> accumulatedResults) {
        LOGGER.log(Level.INFO, "Fetching all people of page: {0}", page);

        return starWarsClient.fetchPeople(page)
                .flatMap(response -> {
                    accumulatedResults.addAll(response.getResults());
                    if (response.getNext() != null) {
                        return fetchAllPeople(page + 1, accumulatedResults);
                    } else {
                        LOGGER.log(Level.INFO, "Fetched people count: {0}", accumulatedResults.size());
                        return Mono.just(accumulatedResults);
                    }
                });
    }

    private Mono<List<PlanetsResponse.Planet>> fetchAllPlanets(int page, List<PlanetsResponse.Planet> accumulatedResults) {
        LOGGER.log(Level.INFO, "Fetching all planets of page: {0}", page);

        return starWarsClient.fetchPlanets(page)
                .flatMap(response -> {
                    accumulatedResults.addAll(response.getResults());
                    if (response.getNext() != null) {
                        return fetchAllPlanets(page + 1, accumulatedResults);
                    } else {
                        LOGGER.log(Level.INFO, "Fetched planets count: {0}", accumulatedResults.size());
                        return Mono.just(accumulatedResults);
                    }
                });
    }

    public void fetchTableDataAndSaveJson(DataType dataType) {
        String fileName = dataType.getDataFileName();

        switch (dataType) {
            case PEOPLE:
                fetchAllPeople(1, new ArrayList<>())
                        .subscribe(results -> jsonFileService.writeToJsonFile(results, fileName));
                break;
            case PLANETS:
                fetchAllPlanets(1, new ArrayList<>())
                        .subscribe(results -> jsonFileService.writeToJsonFile(results, fileName));
                break;
            default:
                throw new IllegalArgumentException("Unsupported data type: " + dataType);
        }
    }

    // Read operations

    public Mono<String> fetchColumns(DataType dataType) throws IOException {
        String fileName = dataType.getColumnsFileName();
        return Mono.just(jsonFileService.readJsonFile(fileName));
    }

    public <T> Mono<String> fetchTableDataFromStorage(DataType dataType, String sortBy, String sortOrder) throws IOException {
        String fileName = dataType.getDataFileName();
        String jsonContent = jsonFileService.readJsonFile(fileName);

        // Exit early if no sorting is needed
        if ((sortBy == null || sortBy.isEmpty()) || (sortOrder == null || sortOrder.isEmpty())) {
            return Mono.just(jsonContent);
        }

        Class<T> type = (Class<T>) dataType.getType();
        List<T> data = objectMapper.readValue(jsonContent, Argument.listOf(type));
        Comparator<T> comparator = getComparator(dataType, sortBy);

        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        Collections.sort(data, comparator);

        return Mono.just(objectMapper.writeValueAsString(data));
    }

    private <T> Comparator<T> getComparator(DataType dataType, String sortBy) {
        switch (dataType) {
            case PEOPLE:
                return (Comparator<T>) getPeopleComparator(sortBy);
            case PLANETS:
                return (Comparator<T>) getPlanetsComparator(sortBy);
            default:
                throw new IllegalArgumentException("Unsupported data type: " + dataType);
        }
    }

    private Comparator<PeopleResponse.Person> getPeopleComparator(String sortBy) {
        switch (sortBy) {
            case "created":
                return Comparator.comparing(PeopleResponse.Person::getCreated);
            default:
                return Comparator.comparing(PeopleResponse.Person::getName);
        }
    }

    private Comparator<PlanetsResponse.Planet> getPlanetsComparator(String sortBy) {
        switch (sortBy) {
            case "created":
                return Comparator.comparing(PlanetsResponse.Planet::getCreated);
            default:
                return Comparator.comparing(PlanetsResponse.Planet::getName);
        }
    }

}
