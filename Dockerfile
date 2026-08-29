# Stage 1: Build the JAR with Maven
FROM maven:3.8.6-openjdk-11-slim AS build
WORKDIR /app

# Copy pom.xml and pre-download dependencies for faster cached builds
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build package
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Minimal runtime image
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
