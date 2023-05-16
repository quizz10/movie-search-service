package com.example.moviesearchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "actorcollection")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtlasActor {
    @Id
    private ObjectId _id;
    private String birthYear;
    private String deathYear;
    private String knownForTitles;
    private String primaryName;
    private String primaryProfession;
}
