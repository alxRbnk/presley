FROM gradle:8.11-jdk21 AS builder

WORKDIR /app

COPY . .

RUN gradle build --no-daemon -x test

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
