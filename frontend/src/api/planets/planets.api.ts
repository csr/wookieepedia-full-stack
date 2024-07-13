import axios from 'axios';
import { useQuery, UseQueryResult, QueryFunctionContext } from 'react-query';
import { apiUrls } from '../api.routes';
import { PlanetColumns, Planet } from './planets.model';
import { SortParameters } from '@/api';

const fetchColumns = async ({ signal }: QueryFunctionContext): Promise<PlanetColumns[]> => {
    try {
        const { data } = await axios.get<PlanetColumns[]>(apiUrls.planetsColumns, { signal });
        return data;
    } catch (error) {
        if (axios.isCancel(error)) {
            console.error("Request canceled:", error.message);
        } else {
            console.error("Error fetching planets columns:", error);
        }
        throw error;
    }
};

const fetchData = async ({ signal }: QueryFunctionContext, sortingParameters?: SortParameters): Promise<Planet[]> => {    
    let params: Record<string, any> = {};

    if (sortingParameters && sortingParameters.sortBy && sortingParameters.sortOrder) {
        params = { sortBy: sortingParameters.sortBy, sortOrder: sortingParameters.sortOrder}
    }

    try {
        const { data } = await axios.get<Planet[]>(apiUrls.planetsData, { signal, params });
        return data;
    } catch (error) {
        if (axios.isCancel(error)) {
            console.error("Request canceled:", error.message);
        } else {
            console.error("Error fetching planets data:", error);
        }
        throw error;
    }
};

export const usePlanetsColumns = (): UseQueryResult<PlanetColumns[], Error> => {
    return useQuery<PlanetColumns[], Error>('planets-columns', fetchColumns);
};

export const usePlanetsData = (searchTerm?: string, sortingParameters?: SortParameters): UseQueryResult<Planet[], Error> => {
    return useQuery<Planet[], Error>({
        queryKey: ['planets-data', sortingParameters],
        queryFn: (context) => fetchData({ ...context }, sortingParameters),
        select: (data: Planet[]) => {
            if (!searchTerm) {
                return data;
            }
            return data.filter(planet => planet.name.toLowerCase().includes(searchTerm.toLowerCase()))
        },
    });
};
