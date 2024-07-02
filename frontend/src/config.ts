const config = {
    // Passing the hosting URL as an environment variable would be ideal, but for some reason
    // the app isn't able to parse the environment variable correctly. 
    backendAppApiUrl: process.env.REACT_APP_BACKEND_APP_API_BASE_URL || "https://wookieepedia-fullstack-app-grbeu.ondigitalocean.app/api",
    backendAppApiPrefix: process.env.REACT_APP_BACKEND_APP_API_PREFIX || "v1",
};
  
export default config;