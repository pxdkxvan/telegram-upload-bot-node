FROM gradle:8.13-jdk21 AS builder

WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY src /app/src

RUN gradle build --no-daemon -x test

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=builder /app/build/libs/node-1.0.jar /app/node.jar
EXPOSE 8082

ENV NODE_NAME=""
ENV NODE_PORT=""
ENV APPLICATION_PROTOCOL=""
ENV CONFIG_HOST=""
ENV CONFIG_PORT=""
ENV RABBITMQ_HOST=""
ENV RABBITMQ_PORT=""
ENV RABBITMQ_USER=""
ENV RABBITMQ_PASSWORD=""
ENV POSTGRES_HOST=""
ENV POSTGRES_PORT=""
ENV POSTGRES_USER=""
ENV POSTGRES_PASSWORD=""
ENV OPENFEIGN_NAME=""
ENV OPENFEIGN_HOST=""
ENV OPENFEIGN_PORT=""
ENV ACTUATOR_ENDPOINT=""

ENTRYPOINT ["java", "-jar", "node.jar"]