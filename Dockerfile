FROM openjdk:17
ADD target/movie-search-service.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]