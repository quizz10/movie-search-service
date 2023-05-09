package com.example.moviesearchservice.service;

import com.example.moviesearchservice.model.AtlasMovie;
import com.example.moviesearchservice.repository.AtlasMovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AtlasMovieService {
    private AtlasMovieRepository movieRepository;

    public List<AtlasMovie> findByOriginalTitle(String originalTitle) {
        return movieRepository.findByOriginalTitleStartsWith(originalTitle);
    }

    public List<AtlasMovie> findByText(String text) {
        return movieRepository.findByText(text);
    }

    public List<AtlasMovie> findAllByText(String text) {
        return movieRepository.findAllByText(text);
    }

    public AtlasMovie findByTconst(String tconst) {
        return movieRepository.findByTconst(tconst);
    }
}
