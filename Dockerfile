# Stage 1: Build the JAR with Maven
FROM maven:3.8.6-openjdk-11-slim AS build
WORKDIR /app

# Copy source and pom together
COPY pom.xml .
COPY src ./src

# Fast build without hanging transfer progress logs
RUN mvn clean package -DskipTests -B --no-transfer-progress

# Stage 2: Minimal runtime image
FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
