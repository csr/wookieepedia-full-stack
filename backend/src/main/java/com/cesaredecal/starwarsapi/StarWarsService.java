package com.cesaredecal.starwarsapi;

import com.cesaredecal.starwarsapi.models.DataType;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.core.type.Argument;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.serde.ObjectMapper;
import reactor.core.publisher.Mono;
import jakarta.inject.Singleton;
import javax.inject.Inject;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class StarWarsService {

    private static final Logger LOGGER = Logger.getLogger(StarWarsService.class.getName());

    private final JsonFileService jsonFileService;
    private final ObjectMapper objectMapper;
    private final Map<DataType, DataService<?>> dataServices;

    @Inject
    public StarWarsService(JsonFileService jsonFileService, ObjectMapper objectMapper, PeopleService peopleService, PlanetsService planetsService) {
        this.jsonFileService = jsonFileService;
        this.objectMapper = objectMapper;
        this.dataServices = new HashMap<>();
        this.dataServices.put(DataType.PEOPLE, peopleService);
        this.dataServices.put(DataType.PLANETS, planetsService);
    }

    @EventListener
    void init(StartupEvent event) {
        fetchTableDataAndSaveJson(DataType.PEOPLE);
        fetchTableDataAndSaveJson(DataType.PLANETS);
    }

    public void fetchTableDataAndSaveJson(DataType dataType) {
        String fileName = dataType.getDataFileName();
        DataService<?> dataService = dataServices.get(dataType);

        if (dataService == null) {
            throw new IllegalArgumentException("Unsupported data type: " + dataType);
        }

        dataService.fetchAll(1, new ArrayList<>())
                .subscribe(
                        results -> jsonFileService.writeToJsonFile(results, fileName),
                        error -> LOGGER.log(Level.SEVERE, "Error fetching " + dataType + " data", error)
                );
    }

    public Mono<String> fetchColumns(DataType dataType) {
        try {
            String fileName = dataType.getColumnsFileName();
            return Mono.just(jsonFileService.readJsonFile(fileName));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error fetching columns data for " + dataType, e);
            return Mono.error(new RuntimeException("Error fetching columns data for " + dataType, e));
        }
    }

    public <T> Mono<String> fetchTableDataFromStorage(DataType dataType, String sortBy, String sortOrder) {
        try {
            String fileName = dataType.getDataFileName();
            String jsonContent = jsonFileService.readJsonFile(fileName);

            if ((sortBy == null || sortBy.isEmpty()) || (sortOrder == null || sortOrder.isEmpty())) {
                return Mono.just(jsonContent);
            }

            Class<T> type = (Class<T>) dataType.getType();
            List<T> data = objectMapper.readValue(jsonContent, Argument.listOf(type));
            DataService<T> dataService = (DataService<T>) dataServices.get(dataType);
            Comparator<T> comparator = dataService.getComparator(sortBy);

            if ("desc".equalsIgnoreCase(sortOrder)) {
                comparator = comparator.reversed();
            }

            Collections.sort(data, comparator);

            return Mono.just(objectMapper.writeValueAsString(data));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error fetching table data for " + dataType, e);
            return Mono.error(new RuntimeException("Error fetching table data for " + dataType, e));
        }
    }
}
