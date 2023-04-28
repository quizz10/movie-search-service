package com.example.moviesearchservice.repository;

import com.example.moviesearchservice.model.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {
    @Query("{'titleType' : 'movie', 'originalTitle' : {$regex:  ?0}}")
    List<Movie> findByOriginalTitleStartsWith(String title);

    @Aggregation(pipeline = {
            "{'$match':{'titleType':'movie'}}",
            "{'$match':{'genres': ?0}}",
            "{'$match':{'averageRating': {$gt: 6, $lt: 10}}}",
            "{ $sample:{ size: 2 } }"

    })
    List<Movie> findTwoTitlesByGenre(String genre);

    @Aggregation(pipeline = {
            "{'$match':{'titleType':'movie'}}",
            "{'$match':{'genres': ?0}}",
            "{'$match':{'averageRating': {$gt: 6, $lt: 10}}}",
            "{ $sample:{ size: ?1 } }"

    })
    List<Movie> findTitlesByGenre(String genre, int amountOfRecommendations);
}
