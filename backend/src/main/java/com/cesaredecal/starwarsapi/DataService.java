package com.cesaredecal.starwarsapi;

import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

public interface DataService<T> {
    Mono<List<T>> fetchAll(int page, List<T> accumulatedResults);
    Comparator<T> getComparator(String sortBy);
}
