package com.example.moviesearchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "moviecollection")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    private ObjectId _id;
    private String tconst;
    private String startYear;
    private String endYear;
    private List<String> genres;
    private String originalTitle;
    private String primaryTitle;
    private float averageRating;
    private String titleType;
    private List<Name> names;
    private List<Cast> casts;

    public List<Name> getNames() {
        if (this.names == null) {
            this.names = new ArrayList<>();
        }
        return names;
    }

    public void setNames(List<Name> names) {
        this.names = names;
    }
}