FROM openjdk:17-jdk-alpine

WORKDIR /backend-app

# Copy the build.gradle, settings.gradle, and gradle.properties files first
# This step helps leverage Docker's caching mechanism
COPY build.gradle settings.gradle gradle.properties ./

COPY gradlew ./
COPY gradle ./gradle

RUN ./gradlew dependencies --no-daemon

COPY . .

RUN ./gradlew build --no-daemon

EXPOSE 8080

CMD ["java", "-jar", "build/libs/wookieepedia-0.1-all.jar"]