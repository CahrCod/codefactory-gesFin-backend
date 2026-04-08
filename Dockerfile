# Build stage
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml first for dependency layer caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:resolve || \
    mvn dependency:resolve || \
    mvn dependency:resolve

# Copy source code
COPY src/ src/

# Build with Maven
RUN mvn clean package -DskipTests -q

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/prueba-reservas-backend-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
