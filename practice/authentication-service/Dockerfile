FROM adoptopenjdk:jdk8u372-b07-ubi-slim

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]