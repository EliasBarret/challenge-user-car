FROM openjdk:11
WORKDIR /app
COPY target/system-car-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]