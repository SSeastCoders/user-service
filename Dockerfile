FROM openjdk:11-jdk

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG SERVICE=users-api
ARG JAR_FILE=${SERVICE}/target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar", "/app.jar"]