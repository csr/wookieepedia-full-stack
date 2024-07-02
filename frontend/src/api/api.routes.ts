import config from "@/config";

export const apiUrls = {
    peopleColumns: `${config.backendAppApiUrl}/${config.backendAppApiPrefix}/people/columns`,
    peopleData: `${config.backendAppApiUrl}/${config.backendAppApiPrefix}/people/data`,
    planetsColumns: `${config.backendAppApiUrl}/${config.backendAppApiPrefix}/planets/columns`,
    planetsData: `${config.backendAppApiUrl}/${config.backendAppApiPrefix}/planets/data`,
}