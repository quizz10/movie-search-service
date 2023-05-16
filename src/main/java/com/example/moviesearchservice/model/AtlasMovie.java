package com.example.moviesearchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "testcollection")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtlasMovie {
    @Id
    private ObjectId _id;
    private String startYear;
    @Indexed
    private String tconst;
    @Indexed
    private List<String> genres;
    @Indexed
    private String originalTitle;
    @Indexed
    private Double averageRating;


}
