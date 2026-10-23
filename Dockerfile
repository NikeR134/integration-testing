FROM eclipse-temurin:21-jre

ARG APP_PORT=8080
ARG NETOLOGY_PROFILE_DEV=true

WORKDIR /app
COPY target/integration-testing-*.jar app.jar

ENV SERVER_PORT=${APP_PORT}
ENV NETOLOGY_PROFILE_DEV=${NETOLOGY_PROFILE_DEV}

EXPOSE ${APP_PORT}

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

