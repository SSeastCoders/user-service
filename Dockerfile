FROM openjdk:11-jdk

RUN adduser --system --group spring
USER spring:spring

# ARG SERVICE=users-api
# ARG JAR_FILE=${SERVICE}/target/*.jar
COPY users-api/target/users-api-1.0.jar app.jar

ENTRYPOINT ["java","-jar", "/app.jar"]