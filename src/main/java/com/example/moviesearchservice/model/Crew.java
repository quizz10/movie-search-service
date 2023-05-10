package com.example.moviesearchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "crewcollection")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crew {
    @Id
    private ObjectId _id;
    private String directors;
    private String tconst;
    private String writers;

}
