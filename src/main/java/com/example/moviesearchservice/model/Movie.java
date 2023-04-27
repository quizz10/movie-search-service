package com.example.moviesearchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "moviecollection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "title-genres-rating", def = "{'titleType' : 1, 'genres': 1, averageRating: 1}")
})
public class Movie {
    @Id
    private ObjectId _id;
    private String startYear;
    private String endYear;
    @Indexed
    private String tconst;
    @Indexed
    private List<String> genres;
    @Indexed
    private String originalTitle;
    @Indexed
    private Double averageRating;
    private String primaryTitle;
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