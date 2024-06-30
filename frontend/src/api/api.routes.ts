import config from "@/config";

export const apiUrls = {
    peopleColumns: `${config.backendAppApiUrl}/${config.backendAppApiPrefix}/people/columns`,
    peopleData: `${config.backendAppApiUrl}/${config.backendAppApiPrefix}/people/data`,
}