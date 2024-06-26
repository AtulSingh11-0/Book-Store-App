FROM openjdk:20
ADD target/Book-Store-App-0.0.1.jar /Book-Store-App-0.0.1.jar
EXPOSE 6969
ENTRYPOINT ["java", "-jar", "Book-Store-App-0.0.1.jar"]