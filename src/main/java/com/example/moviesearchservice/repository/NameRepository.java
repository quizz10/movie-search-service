package com.example.moviesearchservice.repository;

import com.example.moviesearchservice.model.Name;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NameRepository extends MongoRepository<Name, ObjectId> {
    List<Name> findByKnownForTitlesContainsIgnoreCaseAndPrimaryProfessionContainsIgnoreCase(String titles, String actor);

    @Query(value = "{'knownForTitles' : ?0, 'primaryProfession' : { $regex: '^act'}}")
    List<Name> findByKnownForTitlesContainingIgnoreCase(String titles);

    @Query(value = "{'nconst' : ?0, 'primaryProfession' : { $regex: 'act'}}")
    Name findByNconst(String nconst);

}
