FROM eclipse-temurin:17-jdk-alpine
COPY mascotas/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
