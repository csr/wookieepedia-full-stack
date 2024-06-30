# Wookieepedia! 🌌🔫

## Welcome in!

Welcome, Star War fan! We're so excited to have you here. May the _code_ be with you as you navigate through the project! 

Wookieepedia! is a full-stack project divided into two applications:

* A frontend React application written with TypeScript.
* A backend application written with Java and using the XYZ library.

This full-stack application uses data from [swapi](https://swapi.dev/), a Star Wars API.

## Getting Started

First off, ensure you have Docker installed. You can either download [Docker Desktop](https://www.docker.com/products/docker-desktop/) (easier to use but it requires a license if you're working in an enterprise) or [install Docker Engine](https://docs.docker.com/engine/install/ubuntu/#install-using-the-convenience-script) directly.

Let's clone the project:

```bash
git clone https://github.com/csr/wookieepedia-full-stack.git
```

The `docker-compose.yml` file helps you run both the frontend and backend applications at the same time.

```bash
docker compose up
```

If you're getting some authentication error you may first need to 

```bash
docker login
```

## Running the tests
...

## Do you want to build the Docker images one by one?

This step is not necessary because Docker Compose will automatically build and start all the Docker containers for you. However, you can build and run Docker containers one by one if you so decide (useful if you want to try out changes in a single `Dockerfile` file or want to test just the frontend or backend). Let us first navigate to the frontend project:

```bash
cd frontend
```

Let's build the image and assign the `frontend` tag to it:

```bash
docker build -t frontend .
```

Once that is done we can run it:

```bash
docker run -p 6969:3000 frontend
```

Open http://localost:6969 and have fun interacting with Wookieepedia!

You can run the following command to see a list of the running containers on your machine:

```bash
docker ps
```

You can kill it by indicating the container ID:

```bash
docker stop <container_id_here>
```

## Architecture Design

The backend appliation connects to the Star Wars API [swapi](https://swapi.dev/) to fetch the data about the Star Wars characters ("people") and planets. This API isn't very flexible: it doesn't allow to configure the results page size (it is set to 10) and it does not provide any sorting functionality. It is also rate-limited to [10,000 request per day](https://swapi.dev/documentation#rate).

Our Star Wars fans users want to be able to:
* sort some columns (`name` and `created` fields)
* see 15 items per page at once
* search by name

We designed the architecture to provide a pagination with a page size of 15 items and the ability to sort some columns on-demand. 

These are the advantages of our solution:
* The backend application can serve the data and provide lots of flexibility.
* The backend application can scale and is not limited to 10,000 requests a day imposed by the Star Wars API.

These are the disadvantages:
* The backend application will need to make several requests (around 20) when it starts up to collect the data. However, once the data is collected and saved to the disk space, the backend application will no longer make any request to the Star Wars API. Please keep in mind the number of requests won't be too high because the dataset is quite small.
* The backend application will fetch the data solely at startup time. This means that if new data is available in the API the backend application will still serve old data. While this isn't ideal, we make the assumption that the [swapi](https://swapi.dev/) data will not change often. After all, it's not every day that a new Star Wars movie comes out. With modern technologies like Kubernetes it can be easy to create more instances of the application to refresh the data.

## Future improvements
The frontend application should be relatively easy to use. However, there are several things that we can do:
* Provide more user-friendly information in some table rows. `https://swapi.dev/api/films/1/,https://swapi.dev/api/films/2/,https...` isn't very readable. The API could fill in these fields with the actual names of the movies. The `created` field (`2014-12-09T13:50:51.644000Z`) could show user-friendly data (`December 9, 2014`). The homeworld information can be replaced with the actual planet name instead of the API URL.
* Accessibility: ensure the application is fully accessible by testing it with a screen reader.
* Loading state / error notifications.
* Testing it with a real user: we should conduct an interview with a Star War fun to see what they think about the app and if they find it easy to use.

## License

This project is licensed under the terms of the MIT license.
