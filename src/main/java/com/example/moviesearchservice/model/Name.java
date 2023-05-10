package com.example.moviesearchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "namecollection")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Name {
    @Id
    private ObjectId _id;
    private String primaryName;
    private List<String> knownForTitles;
    private List<String> primaryProfession;
    @Indexed
    private String nconst;
}
