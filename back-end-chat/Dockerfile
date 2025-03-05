# Build stage
FROM maven:3.9.9-amazoncorretto-17-alpine AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/back-end-chat-0.0.1-SNAPSHOT.jar /app/back-end-chat-0.0.1-SNAPSHOT.jar

# Command to run the application
CMD ["java", "-jar", "back-end-chat-0.0.1-SNAPSHOT.jar"]