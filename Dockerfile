FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn package -DskipTests
FROM openjdk:17-jdk-slim
COPY --from=build /target/smartContactManager.jar smartContactManager.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","smartContactManager.jar"]
