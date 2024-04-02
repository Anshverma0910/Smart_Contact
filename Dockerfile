FROM openjdk:21
EXPOSE 8080
ADD target/smartContactManager.jar smartContactManager.jar
ENTRYPOINT ["java","-jar","/smartContactManager.jar"]
