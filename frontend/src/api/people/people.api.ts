import axios from 'axios';
import { useQuery, UseQueryResult } from 'react-query';

import { apiUrls } from '../api.routes';
import { PeopleColumns, Person, PeopleDataResponse } from './people.model';

const fetchColumns = async (): Promise<PeopleColumns[]> => {
    try {
        const { data } = await axios.get<PeopleColumns[]>(apiUrls.peopleColumns);
        return data;
    } catch (error) {
        console.error("Error fetching people columns:", error);
        throw error;
    }
};


const fetchData = async (): Promise<Person[]> => {
    try {
        const { data } = await axios.get<Person[]>(apiUrls.peopleData);
        console.log("apiUrls.peopleData:", apiUrls.peopleData)
        return data;
    } catch (error) {
        console.error("Error fetching people data:", error);
        throw error;
    }
};

export const usePeopleColumns = (): UseQueryResult<PeopleColumns[], Error> => {
    return useQuery<PeopleColumns[], Error>('people-columns', fetchColumns);
};

export const usePeopleData = (page: number): UseQueryResult<Person[], Error> => {
    return useQuery<Person[], Error>('people-data', fetchData);
};
