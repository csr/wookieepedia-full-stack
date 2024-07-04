# Build stage
FROM node:18.19.0-slim as build

WORKDIR /frontend-app

# Copy only package.json and package-lock.json to leverage Docker cache
COPY package*.json ./

RUN npm install

# Copy the rest of the application code
COPY . .

# Build the application
RUN npm run build

# Production stage: this stage uses the Nginx server to serve the static files
FROM nginx:alpine

# Copy built application from the build stage
COPY --from=build /frontend-app/build /usr/share/nginx/html

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]
