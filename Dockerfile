# Etapa de build (construir o jar)
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# Etapa de execução (rodar o jar)
FROM eclipse-temurin:21-jdk

WORKDIR /app
EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
