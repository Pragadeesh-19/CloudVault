FROM openjdk:22-jdk-alpine

WORKDIR /app

COPY target/myapp.jar

