package com.example.moviesearchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtlasCompiled {
    private List<AtlasMovie> movies = new ArrayList<>();
    private List<AtlasActor> actors = new ArrayList<>();
}
