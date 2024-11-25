FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR file to the container
COPY build/libs/*.jar app.jar

# Expose the application port (default Spring Boot port)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
