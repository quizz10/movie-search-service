package com.example.moviesearchservice.repository;

import com.example.moviesearchservice.model.AtlasMovie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtlasMovieRepository extends MongoRepository<AtlasMovie, ObjectId> {
    @Query("{'originalTitle' : {$regex:  ?0}}")
    List<AtlasMovie> findByOriginalTitleStartsWith(String title);

    AtlasMovie findByTconst(String tconst);

    @Aggregation(pipeline = {
            "{$search: {index: 'ogtitle', text: {query: ?0, path: 'originalTitle', }}}",
            "{$sample: {size: 40}}"

    })
    List<AtlasMovie> findByText(String text);
    @Aggregation(pipeline = {
            "{$search: {index: 'titleandgenres', text:{query:?0, path: {wildcard: '*'}}}}"
         //   "{$sample: {size: 40}}"

    })
    List<AtlasMovie> findAllByText(String text);

}
