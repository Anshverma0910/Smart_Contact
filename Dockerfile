FROM maven:3.8.8-eclipse-temurin-21-alpine
COPY target/smartContactManager.jar smartContactManager.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/smartContactManager.jar"]
