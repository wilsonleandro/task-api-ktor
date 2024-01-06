FROM openjdk:11-jre-slim

WORKDIR /server

COPY target/*.jar /server/app.jar

EXPOSE 8080

CMD java -XX:+UseContainerSupport -jar app.jar
