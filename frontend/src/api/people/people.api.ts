import axios from 'axios';
import { useQuery, UseQueryResult, QueryFunctionContext } from 'react-query';
import { apiUrls } from '../api.routes';
import { PeopleColumns, Person } from './people.model';
import { SortParameters } from '@/api';

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

const fetchData = async ({ signal }: QueryFunctionContext, sortingParameters?: SortParameters): Promise<Person[]> => {    
    let params: Record<string, any> = {};

    if (sortingParameters && sortingParameters.sortBy && sortingParameters.sortOrder) {
        params = { sortBy: sortingParameters.sortBy, sortOrder: sortingParameters.sortOrder}
    }

    try {
        const { data } = await axios.get<Person[]>(apiUrls.peopleData, { signal, params });
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

export const usePeopleData = (searchTerm?: string, sortingParameters?: SortParameters): UseQueryResult<Person[], Error> => {
    return useQuery<Person[], Error>({
        queryKey: ['people-data', sortingParameters],
        queryFn: (context) => fetchData({ ...context }, sortingParameters),
        select: (data: Person[]) => {
            if (!searchTerm) {
                return data;
            }
            return data.filter(person => person.name.toLowerCase().includes(searchTerm.toLowerCase()))
        },
    });
};
