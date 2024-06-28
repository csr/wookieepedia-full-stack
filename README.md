# wookieepedia-full-stack

## Welcome in!

Welcome, Star War fan! We're so excited to have you here. Wookieepedia is a full-stack project divided into two applications:

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
docker-compose up
```

If you're getting some authentication error you may first need to 

```bash
docker login
```

## Running the tests
...

## Extra tips

You can build and run Docker containers one by one. Let us first navigate to the frontend project:

```bash
cd frontend
```

Let's build the image:

```bash
docker build -t frontend .
```

And now it's time to run it:

```bash
docker run -p 6969:3000 frontend
```

It will be available on http://localost:6969.

## Behind the scenes

We make the assumption that the [swapi](https://swapi.dev/) data will not change often. After all, it's not every day that a new Star Wars movie comes out. We can therefore save the entire dataset in the backend application and then serve from that dataset. This way our backend application can handle a lot of traffic and more tan the [10,000 request per day](https://swapi.dev/documentation#rate) supported by the [swapi](https://swapi.dev/).

## License

This project is licensed under the terms of the MIT license.
