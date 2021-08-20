FROM maven
WORKDIR /var/www

COPY . .
RUN mvn clean -DskipTests install
#EXPOSE 8222

CMD [ "mvn", "-pl", "users-api", "-P", "dev", "spring-boot:run"]
