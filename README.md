# Wookieepedia! 🌌🔫

## Overview

Wookieepedia! is a full-stack project for Star Wars fans and it consists of two different applications containerized with Docker. 

You can access the [live web app](https://wookieepedia-fullstack-app-grbeu.ondigitalocean.app/) or run it locally on your computer using Docker (instructions below).

| Application     | Description                                  | Application Port | Docker Compose Port |
|-----------------|----------------------------------------------|------------------|----------------------|
| Frontend app    | React application written in TypeScript. It features two tables (🤖 _people_ and 🪐 _planets_). It uses axios to make HTTP REST API requests and React Query to manage and cache the API responses. | 3000             | 6969                 |
| Backend app     | Micronaut application written in Java. It servers endpoints that return the columns and data of the _people_ and _planets_ tables. It uses data from [swapi](https://swapi.dev/), a Star Wars API.  | 8080             | 8080                 |

## Project structure

Each application has its own folder at the project root level:

* `frontend`: this is the React frontend app
* `backend`: this is the Java backend app

This project provides a Docker Compose file that helps you build the containers and run them locally in a few simple steps. Both the `frontend` and `backend` folder also feature a `Dockerfile` that can be used to build the application locally. 

You can also find the images inside the [cesaredecal/wookieepedia-full-stack](https://hub.docker.com/repository/docker/cesaredecal/wookieepedia-full-stack) Docker Hub public repository (the images are tagged `frontend-latest` and `backend-latest`).

👉 Ensure you have Docker and Docker Compose installed. You can either download [Docker Desktop](https://www.docker.com/products/docker-desktop/) (easier to use but it requires a license if you're working in an enterprise) or [install Docker Engine](https://docs.docker.com/engine/install/ubuntu/#install-using-the-convenience-script) directly.

## Running the project

Let's clone the project and navigate inside of it:

```bash
$ git clone https://github.com/csr/wookieepedia-full-stack.git
$ cd wookieepedia-full-stack
```

The `docker-compose.yml` file helps you run both the frontend and backend applications at the same time.

```bash
$ docker compose up
```

If you're getting some authentication error you may first need to 

```bash
$ docker login
```

You should be able to open the app at the following address http://localhost:6969.

## High-level project overview

The backend application fetches all the _people_ and _planets_ data from the 3rd party Star Wars API on startup. It then saves the data as JSON in two files: `people_data.json` and `planets_data.json`. All data served to the frontend app comes from the JSON files stored locally. No subsequent calls to the Star Wars API are made. The frontend project sends requests exclusively to the backend application and does not interact directly with the Star Wars API. The columns metadata for both _people_ and _planets_ is stored in `people_metadata.json` and `planets_metadata.json`. The columns metadata contains information on whether a given column is `sortable`, as well as a description on each column that is displayed in the UI.

## Design considerations

This architecture design isn't perfect. Here is why the application works the way it does:

* The Star Wars API dataset isn't very big. By saving the data locally we can serve it quickly to users and have more flexibility with the way we serve the data. The Star Wars API isn't very flexible: it doesn't support a page size of 15 items per page nor it supports sorting. By storing the data locally we can sort the data without having to retrieve it every single time.
* The backend application can scale and is not limited to [10,000 request per day](https://swapi.dev/documentation#rate) imposed by the Star Wars API.

However, there are some downsides:

* The backend application will need to make several requests (around 20) when it starts up to collect the data. However, once the data is collected and saved to the disk space, the backend application will no longer make any request to the Star Wars API. Please keep in mind the number of requests won't be too high because the dataset (in other words, the number of pages) is quite small.
* The backend application will fetch the data solely at startup time. This means that if new data is available in the API the backend application will still serve old data. While this isn't ideal, we make the assumption that the [swapi](https://swapi.dev/) data will not change often. After all, it's not every day that a new Star Wars movie comes out. With modern technologies like Kubernetes it can be easy to create more instances of the application to refresh the data. However, it's **not as easy to detect once the data changes**.

## Frontend design considerations

Search and pagination are handled on the frontend side, while sorting is done on the backend as per requirement. A client-side search is pretty fast, but it comes with a downside: the backend will always have to return the entire dataset no matter if the user is searching locally or not. The pagination is done on the frontend because it was simpler and easier to sync the UI state with the backend state, but in the future we can explore ways to paginate date (especially if the dataset grows in size!). The frontend app is also designed to be responsive and work well on mobile devices (I hope!).

## Building the Docker image one by one

This step is not necessary because Docker Compose will automatically build and start all the Docker containers for you. However, you can build and run Docker containers one by one if you so decide (useful if you want to try out changes in a single `Dockerfile` file or want to test just the frontend or backend). Let us first navigate to the frontend project:

```bash
$ cd frontend
```

Let's build the image and assign the `frontend` tag to it:

```bash
$ docker build -t frontend .
```

Once that is done we can run it:

```bash
$ docker run -p 6969:3000 frontend
```

Open http://localost:6969 and have fun interacting with Wookieepedia!

You can run the following command to see a list of the running containers on your machine:

```bash
$ docker ps
```

You can kill it by indicating the container ID:

```bash
$ docker stop <container_id_here>
```

## Future improvements
The frontend application should be relatively easy to use. However, there are several things that we can do:
* Provide more user-friendly information in some table rows. `https://swapi.dev/api/films/1/,https://swapi.dev/api/films/2/,https...` isn't very readable. The API could fill in these fields with the actual names of the movies. The `created` field (`2014-12-09T13:50:51.644000Z`) could show user-friendly data (`December 9, 2014`). The homeworld information can be replaced with the actual planet name instead of the API URL.
* Accessibility: ensure the application is fully accessible by testing it with a screen reader.
* Loading state / error notifications.
* Testing it with a real user: we should conduct an interview with a Star Wars fun to see what they think about the app and if they find it easy to use.
* The pagination could be implemented on the backend side to help reduce the amount of data transfered. In this case the list of people and planets is quite small but bigger datasets may benefit from a pagination implemented on the backend, although that increases the number of requests the frontend needs to make (if the user decides to navigate to a different page).

## License

This project is licensed under the terms of the MIT license.
