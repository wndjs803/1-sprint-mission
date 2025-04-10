# (1) Builder Stage
FROM gradle:7.6.0-jdk17 AS builder
WORKDIR /app
COPY . /app
RUN chmod +x gradlew
RUN ./gradlew build --no-daemon


# (2) Runtime Stage
FROM amazoncorretto:17.0.7-alpine
WORKDIR /app

ARG PROJECT_NAME
ARG PROJECT_VERSION
ARG JVM_OPTS

COPY --from=builder /app/build/libs/*.jar ${PROJECT_NAME}-${PROJECT_VERSION}.jar
COPY .env /app
EXPOSE 80

CMD java ${JVM_OPTS} -jar ${PROJECT_NAME}-${PROJECT_VERSION}.jar
