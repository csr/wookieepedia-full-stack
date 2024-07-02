package com.cesaredecal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
public class PlanetsResponse {

    private int count;
    private String next;
    private String previous;
    private List<Planet> results;

    public PlanetsResponse(int count, String next, String previous, List<Planet> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    @JsonProperty("count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @JsonProperty("next")
    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @JsonProperty("previous")
    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    @JsonProperty("results")
    public List<Planet> getResults() {
        return results;
    }

    public void setResults(List<Planet> results) {
        this.results = results;
    }

    @Serdeable
    public static class Planet {
        private String name;
        private String rotationPeriod;
        private String orbitalPeriod;
        private String diameter;
        private String climate;
        private String gravity;
        private String terrain;
        private String surfaceWater;
        private String population;
        private List<String> residents;
        private List<String> films;
        private String created;
        private String edited;
        private String url;

        public Planet() {}

        public Planet(String name, String rotationPeriod, String orbitalPeriod, String diameter, String climate,
                      String gravity, String terrain, String surfaceWater, String population,
                      List<String> residents, List<String> films, String created, String edited, String url) {
            this.name = name;
            this.rotationPeriod = rotationPeriod;
            this.orbitalPeriod = orbitalPeriod;
            this.diameter = diameter;
            this.climate = climate;
            this.gravity = gravity;
            this.terrain = terrain;
            this.surfaceWater = surfaceWater;
            this.population = population;
            this.residents = residents;
            this.films = films;
            this.created = created;
            this.edited = edited;
            this.url = url;
        }

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("rotation_period")
        public String getRotationPeriod() {
            return rotationPeriod;
        }

        public void setRotationPeriod(String rotationPeriod) {
            this.rotationPeriod = rotationPeriod;
        }

        @JsonProperty("orbital_period")
        public String getOrbitalPeriod() {
            return orbitalPeriod;
        }

        public void setOrbitalPeriod(String orbitalPeriod) {
            this.orbitalPeriod = orbitalPeriod;
        }

        @JsonProperty("diameter")
        public String getDiameter() {
            return diameter;
        }

        public void setDiameter(String diameter) {
            this.diameter = diameter;
        }

        @JsonProperty("climate")
        public String getClimate() {
            return climate;
        }

        public void setClimate(String climate) {
            this.climate = climate;
        }

        @JsonProperty("gravity")
        public String getGravity() {
            return gravity;
        }

        public void setGravity(String gravity) {
            this.gravity = gravity;
        }

        @JsonProperty("terrain")
        public String getTerrain() {
            return terrain;
        }

        public void setTerrain(String terrain) {
            this.terrain = terrain;
        }

        @JsonProperty("surface_water")
        public String getSurfaceWater() {
            return surfaceWater;
        }

        public void setSurfaceWater(String surfaceWater) {
            this.surfaceWater = surfaceWater;
        }

        @JsonProperty("population")
        public String getPopulation() {
            return population;
        }

        public void setPopulation(String population) {
            this.population = population;
        }

        @JsonProperty("residents")
        public List<String> getResidents() {
            return residents;
        }

        public void setResidents(List<String> residents) {
            this.residents = residents;
        }

        @JsonProperty("films")
        public List<String> getFilms() {
            return films;
        }

        public void setFilms(List<String> films) {
            this.films = films;
        }

        @JsonProperty("created")
        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        @JsonProperty("edited")
        public String getEdited() {
            return edited;
        }

        public void setEdited(String edited) {
            this.edited = edited;
        }

        @JsonProperty("url")
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
