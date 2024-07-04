package com.cesaredecal.starwarsapi.models;

public enum DataType {
    PEOPLE("people_data.json", "people_metadata.json", PeopleResponse.Person.class),
    PLANETS("planets_data.json", "planets_metadata.json", PlanetsResponse.Planet.class);

    private final String dataFileName;
    private final String columnsFileName;
    private final Class<?> type;

    <T> DataType(String dataFileName, String columnsFileName, Class<T> type) {
        this.dataFileName = dataFileName;
        this.columnsFileName = columnsFileName;
        this.type = type;
    }

    public String getDataFileName() {
        return dataFileName;
    }

    public String getColumnsFileName() {
        return columnsFileName;
    }

    public Class<?> getType() {
        return type;
    }
}
