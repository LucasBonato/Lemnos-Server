FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/Lemnos-Server-0.1.0.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]