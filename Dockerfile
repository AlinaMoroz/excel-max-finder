FROM openjdk:21-jdk-slim

WORKDIR /app

COPY /build/libs/excel-max-finder-0.0.1-SNAPSHOT.jar /app/excel-max-finder.jar
COPY /files /app/files



EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/excel-max-finder.jar"]