package com.example.moviesearchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "castcollection")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cast {
    @Id
    ObjectId _id;
    @Indexed
    String tconst;
    int ordering;
    String nconst;
    String category;
    String job;
    String characters;
}
