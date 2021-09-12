FROM openjdk:11
LABEL maintainer="amanda.rolon@smoothstack.com"
# RUN addgroup -S spring adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/user-service/users-api/target/*.jar"]
