const API_PREFIX = "api/v1"

const backendUrl = process.env.BACKEND_APP_API_BASE_URL || "http://localhost:8080"

export const apiUrls = {
    peopleColumns: `${backendUrl}/${API_PREFIX}/people/columns`,
    peopleData: `${backendUrl}/${API_PREFIX}/people/data`,
}