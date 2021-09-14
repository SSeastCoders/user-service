# FROM openjdk:11
# LABEL maintainer="amanda.rolon@smoothstack.com"
# RUN adduser --system --group spring
# USER spring:spring
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/user-service/users-api/target/*.jar"]
#

FROM openjdk:11-jdk

RUN adduser --system --group spring
USER spring:spring

ARG SERVICE=users-api
ARG JAR_FILE=${SERVICE}/target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar", "/app.jar"]


# ADD target/FeedbackService.jar app.jar
# EXPOSE 8081
# ENTRYPOINT ["java","-jar","/app.jar"]
