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
    private ObjectId _id;
    @Indexed
    private String tconst;
    private int ordering;
    private String nconst;
    private String category;
    private String job;
    private String characters;
}
