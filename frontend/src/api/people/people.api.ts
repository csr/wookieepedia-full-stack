import axios from 'axios';
import { useQuery, UseQueryResult, QueryFunctionContext } from 'react-query';

import { apiUrls } from '../api.routes';
import { PeopleColumns, Person, PeopleDataResponse } from './people.model';

const fetchColumns = async ({ signal }: QueryFunctionContext): Promise<PeopleColumns[]> => {
    try {
        const { data } = await axios.get<PeopleColumns[]>(apiUrls.peopleColumns, { signal });
        return data;
    } catch (error) {
        if (axios.isCancel(error)) {
            console.error("Request canceled:", error.message);
        } else {
            console.error("Error fetching people columns:", error);
        }
        throw error;
    }
};

const fetchData = async ({ signal, meta }: QueryFunctionContext): Promise<Person[]> => {
    const search = meta?.search;
    try {
        const { data } = await axios.get<Person[]>(`${apiUrls.peopleData}`, { signal, params: {search} });
        console.log("apiUrls.peopleData:", apiUrls.peopleData);
        return data;
    } catch (error) {
        if (axios.isCancel(error)) {
            console.error("Request canceled:", error.message);
        } else {
            console.error("Error fetching people data:", error);
        }
        throw error;
    }
};

export const usePeopleColumns = (): UseQueryResult<PeopleColumns[], Error> => {
    return useQuery<PeopleColumns[], Error>('people-columns', fetchColumns);
};

export const usePeopleData = (search?: string): UseQueryResult<Person[], Error> => {
    return useQuery<Person[], Error>({
        queryKey: ['people-data', search],
        queryFn: (context) => fetchData({ ...context, meta: { search } }),
    });
};
