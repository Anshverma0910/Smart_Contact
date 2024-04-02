FROM maven:3.8.8-eclipse-temurin-21-alpine
VOLUME /tmp
COPY target/*.jar smartCotact.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/smartContact.jar"]
