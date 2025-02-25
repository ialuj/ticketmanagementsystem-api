FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY ./taregt/ticketmanagementsystem-0.0.1-SNAPSHOT.jar /app/ticketmanagementsystem-api.jar
CMD ["java", "-jar", "ticketmanagementsystem-api.jar"]
