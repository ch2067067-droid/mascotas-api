# Etapa 1: compilar
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY mascotas/ .
RUN mvn clean package -DskipTests

# Etapa 2: ejecutar
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
