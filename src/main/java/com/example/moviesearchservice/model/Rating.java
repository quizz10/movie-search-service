package com.example.moviesearchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ratingcollection")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    ObjectId _id;
    float averageRating;
    int numVotes;
    String tconst;
}
