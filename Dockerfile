#FROM openjdk:18
#EXPOSE 8080
#WORKDIR /app
#COPY ./target/url-shortener-0.0.1-SNAPSHOT.jar /app
##COPY /src/main/resources/database.properties /app/config/database.properties
#CMD ["java","-jar","url-shortener-0.0.1-SNAPSHOT.jar"]

FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]