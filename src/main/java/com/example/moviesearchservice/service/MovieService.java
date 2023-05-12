package com.example.moviesearchservice.service;

import com.example.moviesearchservice.model.Cast;
import com.example.moviesearchservice.model.Movie;
import com.example.moviesearchservice.model.Name;
import com.example.moviesearchservice.model.User;
import com.example.moviesearchservice.repository.CastRepository;
import com.example.moviesearchservice.repository.MovieRepository;
import com.example.moviesearchservice.repository.NameRepository;
import com.example.moviesearchservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieService {
    private final NameRepository nameRepository;
    private final MovieRepository movieRepository;
    private final CastRepository castRepository;
    private final UserRepository userRepository;
    private final GenreService genreService;
    private final UserService userService;
    private final char TITLE_REGEX = '^';

    public List<Movie> findByOriginalTitleAndUserId(long userId, String originalTitle) {
        User user = userService.findUserById(userId);

        List<Movie> movies = movieRepository.findByOriginalTitleStartsWith(TITLE_REGEX + originalTitle);
        List<Movie> compiledMovies = compileMovies(movies);
        storeGenreData(user, movies);

        return compiledMovies;
    }
//TODO: Se till att det är "optional" om man vill ha med skådespelare etc, t.ex med en bool.
    public List<Movie> findByTitle(String title) {
        List<Movie> movies = movieRepository.findByOriginalTitleStartsWith(TITLE_REGEX + title);

        return compileMovies(movies);
    }

    public List<Movie> findRecommendedTitlesByTopGenresAndUserId(long userId) {
        List<Map.Entry<String, Integer>> genreList = genreService.findTopThreeGenres(userId);
        List<Movie> movies = new ArrayList<>();
        int amountOfRecommendations = 3;
        for (Map.Entry<String, Integer> genre : genreList) {
            movies.addAll(movieRepository.findTitlesByGenre(genre.getKey(), amountOfRecommendations--));
        }
        return compileMovies(movies);
    }

    private void storeGenreData(User user, List<Movie> movies) {
        final String EMPTY_GENRE = "\\N";
        final int ADD_ONE = 1;
        movies.stream().flatMap(movie -> movie.getGenres().stream())
                .filter(g -> !g.startsWith(EMPTY_GENRE))
                .forEach(genre -> user.getGenres()
                        .merge(genre, ADD_ONE, Integer::sum));
        userRepository.save(user);
    }

    private List<Movie> compileMovies(List<Movie> movies) {
        movies.forEach(movie -> {
            movie.setCasts(findCast(movie.getTconst()));
            movie.getNames().addAll(findActors(movie.getCasts()));
        });
        return movies;
    }


    private List<Cast> findCast(String tconst) {
        return castRepository.findByTconst(tconst);
    }

    private List<Name> findActors(List<Cast> casts) {
        return casts.stream()
                .map(cast -> nameRepository.findByNconst(cast.getNconst()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
