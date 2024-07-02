import axios from 'axios';
import { useQuery, UseQueryResult, QueryFunctionContext } from 'react-query';
import { GridSortDirection } from '@mui/x-data-grid';
import { apiUrls } from '../api.routes';
import { PeopleColumns, Person } from './people.model';

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
    const sortBy = meta?.sortBy;
    const sortOrder = meta?.sortOrder;

    try {
        const { data } = await axios.get<Person[]>(apiUrls.peopleData, { signal, params: {sortBy, sortOrder} });
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

export const usePeopleData = (searchTerm?: string, sortBy?: string, sortOrder?: GridSortDirection): UseQueryResult<Person[], Error> => {
    return useQuery<Person[], Error>({
        queryKey: ['people-data', searchTerm, sortBy, sortOrder],
        queryFn: (context) => fetchData({ ...context, meta: {sortBy, sortOrder} }),
        select: (data: Person[]) => {
            if (!searchTerm) {
                return data;
            }
            return data.filter(person => person.name.toLowerCase().includes(searchTerm.toLowerCase()))
        },
    });
};
