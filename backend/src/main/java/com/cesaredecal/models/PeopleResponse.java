package com.cesaredecal.models;

import io.micronaut.core.annotation.Introspected;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class PeopleResponse {

    private int count;
    private String next;

    private List<Person> results;

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

    @JsonProperty("results")
    public List<Person> getResults() {
        return results;
    }

    public void setResults(List<Person> results) {
        this.results = results;
    }

    @Serdeable
    public static class Person {

        private String name;
        private String height;
        private String mass;
        private String hairColor;
        private String skinColor;
        private String eyeColor;
        private String birthYear;
        private String gender;
        private String homeworld;
        private List<String> films;
        private List<String> species;
        private List<String> vehicles;
        private List<String> starships;
        private String created;
        private String edited;
        private String url;

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("height")
        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        @JsonProperty("mass")
        public String getMass() {
            return mass;
        }

        public void setMass(String mass) {
            this.mass = mass;
        }

        @JsonProperty("hair_color")
        public String getHairColor() {
            return hairColor;
        }

        public void setHairColor(String hairColor) {
            this.hairColor = hairColor;
        }

        @JsonProperty("skin_color")
        public String getSkinColor() {
            return skinColor;
        }

        public void setSkinColor(String skinColor) {
            this.skinColor = skinColor;
        }

        @JsonProperty("eye_color")
        public String getEyeColor() {
            return eyeColor;
        }

        public void setEyeColor(String eyeColor) {
            this.eyeColor = eyeColor;
        }

        @JsonProperty("birth_year")
        public String getBirthYear() {
            return birthYear;
        }

        public void setBirthYear(String birthYear) {
            this.birthYear = birthYear;
        }

        @JsonProperty("gender")
        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        @JsonProperty("homeworld")
        public String getHomeworld() {
            return homeworld;
        }

        public void setHomeworld(String homeworld) {
            this.homeworld = homeworld;
        }

        @JsonProperty("films")
        public List<String> getFilms() {
            return films;
        }

        public void setFilms(List<String> films) {
            this.films = films;
        }

        @JsonProperty("species")
        public List<String> getSpecies() {
            return species;
        }

        public void setSpecies(List<String> species) {
            this.species = species;
        }

        @JsonProperty("vehicles")
        public List<String> getVehicles() {
            return vehicles;
        }

        public void setVehicles(List<String> vehicles) {
            this.vehicles = vehicles;
        }

        @JsonProperty("starships")
        public List<String> getStarships() {
            return starships;
        }

        public void setStarships(List<String> starships) {
            this.starships = starships;
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
