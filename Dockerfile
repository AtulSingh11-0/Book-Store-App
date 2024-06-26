FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-oracle
WORKDIR /app
COPY --from=build /app/target/Book-Store-App-0.0.1.jar ./Book-Store-App-0.0.1.jar
EXPOSE 6969
ENTRYPOINT ["java", "-jar", "Book-Store-App-0.0.1.jar"]
