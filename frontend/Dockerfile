# Potential improvement: use multi-stage builds
# We can reduce the final size of the Docker image. The NodeJS image is pretty big because it comes with several packages and tools
# that we may need to build our application but that we don't necessarily need in the final image.
FROM node:18.19.0-slim

WORKDIR /frontend-app

# Copy only package.json and package-lock.json to leverage Docker cache
COPY package*.json ./

RUN npm i

COPY . .

CMD ["npm", "run", "start"]
