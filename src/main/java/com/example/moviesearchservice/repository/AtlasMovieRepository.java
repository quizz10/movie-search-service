package com.example.moviesearchservice.repository;

import com.example.moviesearchservice.model.AtlasMovie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtlasMovieRepository extends MongoRepository<AtlasMovie, ObjectId> {
    @Aggregation(pipeline = {
                      "{'$search': {index: 'title', 'text':" +
                              "{'query': ?0,'path':'originalTitle'}}}", "{'$limit':12}",
            "{'$project': " +
                    "{'averageRating':1, " +
                    "'startYear': 1, " +
                    "'tconst': 1, " +
                    "'genres': 1, " +
                    "'originalTitle': 1, " +
                    "'score': " +
                    "{'$meta': 'searchScore'}}}"
              })
    List<AtlasMovie> findByOriginalTitle(String title);
}

