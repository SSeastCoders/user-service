FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=users-api/target/*.jar
COPY ${JAR_FILE} app.jar
# remove spring profile parameter once this is pushed to develop
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=dev", "/app.jar"]