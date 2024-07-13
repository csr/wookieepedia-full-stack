# Stage 1: Build the native executable using GraalVM
FROM ghcr.io/graalvm/native-image:latest as build

WORKDIR /backend-app

RUN microdnf install findutils

# Copy the build.gradle, settings.gradle, and gradle.properties files first
# This step helps leverage Docker's caching mechanism
COPY build.gradle settings.gradle gradle.properties ./

COPY gradlew ./
COPY gradle ./gradle

RUN ./gradlew dependencies --no-daemon

COPY . .

RUN ./gradlew build --no-daemon

# Create the native image
RUN native-image --no-fallback -H:Name=wookieepedia -H:Class=com.cesaredecal.Application -cp build/libs/wookieepedia-0.1-all.jar

# Stage 2: Create the final lightweight image
FROM debian:buster-slim

WORKDIR /backend-app

# Copy the native executable from the build stage
COPY --from=build /backend-app/wookieepedia /backend-app/wookieepedia

# Expose the application port
EXPOSE 8080

# Set the entry point to the native executable
ENTRYPOINT ["/backend-app/wookieepedia"]
