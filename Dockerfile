FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn package -DskipTests
FROM openjdk:17-jdk-slim
COPY --from=build /target/smartCotactManager-0.0.1-SNAPSHOT.jar smartContactManager.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","smartContactManager.jar"]
