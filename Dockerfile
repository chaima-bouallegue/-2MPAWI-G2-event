FROM eclipse-temurin:11-jdk
WORKDIR /app
COPY target/eventsProject-0.0.3-SNAPSHOT.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]