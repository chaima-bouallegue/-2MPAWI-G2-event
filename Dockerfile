# Version optimisée avec JRE au lieu de JDK
FROM eclipse-temurin:11-jre-alpine
WORKDIR /app
COPY target/eventsProject-0.0.3-SNAPSHOT.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]