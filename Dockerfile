# FROM openjdk:11
# LABEL maintainer="amanda.rolon@smoothstack.com"
# RUN adduser --system --group spring
# USER spring:spring
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/user-service/users-api/target/*.jar"]
#

FROM openjdk:11-jdk

# RUN adduser --system --group spring
# USER spring:spring

ARG SERVICE=users-api
ARG JAR_FILE=${SERVICE}/target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar", "/app.jar"]


# # cache the dependcies aside from core-library
# FROM maven:3.8.2-openjdk-11 as dependency
# WORKDIR /app
#
# COPY core-library/pom.xml core-library/pom.xml
# COPY users-api/pom.xml users-api/pom.xml
# COPY pom.xml .
# RUN mvn dependency:go-offline -DexcludeArtifactIds=core-library
#
# # stage 2 copy dependencies from cache and build project
# FROM maven:3.8.2-openjdk-11 as builder
# WORKDIR /app
# COPY --from=dependency /root/.m2 /root/.m2
# COPY --from=dependency /app /app
# COPY core-library/src /app/core-library/src
# COPY users-api/src /app/users-api/src
# RUN mvn clean install -DskipTests
#
# FROM openjdk:11-jdk
# WORKDIR /app
# COPY --from=builder /app/*-api/target/*.jar /app/app.jar
# ADD https://raw.githubusercontent.com/eficode/wait-for/v2.1.3/wait-for /wait-for
# RUN chmod +x /wait-for
# RUN apt-get -q update && apt-get -qy install netcat
# CMD [ "/wait-for", "mysql:3307", "-t", "300", "--", "java", "-jar", "/app/app.jar" ]
