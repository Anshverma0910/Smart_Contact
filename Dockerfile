FROM jelastic/maven:3.9.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests
COPY --from=build /target/smartContact-0.0.1-SNAPSHOT.jar smartContact.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","smartContact.jar"]
