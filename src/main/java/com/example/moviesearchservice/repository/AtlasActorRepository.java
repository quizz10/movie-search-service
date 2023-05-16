package com.example.moviesearchservice.repository;

import com.example.moviesearchservice.model.AtlasActor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AtlasActorRepository extends MongoRepository<AtlasActor, ObjectId> {
    @Aggregation(pipeline = {
            "{'$search': {index: 'actorname', 'text':" +
                    "{'query': ?0,'path':'primaryName'}}}", "{'$limit':12}",
            "{'$project': " +
                    "{'birthYear': 1, " +
                    "'deathYear': 1, " +
                    "'knownForTitles': 1, " +
                    "'primaryName': 1, " +
                    "'score': " +
                    "{'$meta': 'searchScore'}}}"

    })
    List<AtlasActor> findByActor(String actorName);
}
