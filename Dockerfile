FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
COPY --from=build /target/smartContact-0.0.1-SNAPSHOT.jar smartContact.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","smartContact.jar"]
