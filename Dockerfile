# consider also eclipse-temurin:17-jdk-alpine
FROM openjdk:17.0.1-jdk-slim@sha256:565d3643a78a657ca03e85c110af9579a07e833d6bcc14f475249c521b5c5d74

RUN addgroup --system reactivegroup && adduser --system reactive --ingroup reactivegroup
USER reactive

ENV APP_NAME 'spring-web-reactive-example'
ARG APP_VERSION
ARG JAR_FILE=/build/libs/${APP_NAME}-${APP_VERSION}.jar
COPY ${JAR_FILE}  /reactive-app.jar

USER reactive:reactivegroup

ENTRYPOINT java -Xms128M -Xmx1G -jar /reactive-app.jar
