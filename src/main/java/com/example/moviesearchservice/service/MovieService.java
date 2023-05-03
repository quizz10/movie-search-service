package com.example.moviesearchservice.service;

import com.example.moviesearchservice.model.Cast;
import com.example.moviesearchservice.model.Movie;
import com.example.moviesearchservice.model.Name;
import com.example.moviesearchservice.model.User;
import com.example.moviesearchservice.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

@Service
@AllArgsConstructor
public class MovieService {
    private final NameRepository nameRepository;
    private final MovieRepository movieRepository;
    private final CastRepository castRepository;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final char TITLE_REGEX = '^';


    public User newUser() {
        User user = new User();
        user.setUserId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        return userRepository.save(user);
    }

    public Optional<User> findUserById(long userId) {

        return Optional.ofNullable(userRepository.findUserByUserId(userId).orElseThrow(IllegalArgumentException::new));
    }

    public List<Movie> findByOriginalTitle(long userId, String originalTitle) {
        User user = findUserById(userId).get();

        List<Movie> movies = movieRepository.findByOriginalTitleStartsWith(TITLE_REGEX + originalTitle);
        List<Movie> compiledMovies = compileMovies(movies);
        storeGenreData(user, movies);

        return compiledMovies;
    }

    public List<Movie> compileMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            movie.setCasts(getCast(movie.getTconst()));
            movie.getNames().addAll(getActors(movie.getCasts()));
        }
        return movies;
    }

    public List<Movie> findByTitle(String title) {
        return movieRepository.findByOriginalTitleStartsWith(TITLE_REGEX + title);
    }

    public List<Movie> findRecommendedTitlesByGenres(long userId) {
        User user = findUserById(userId).get();
        Set<Entry<String, Integer>> set = sortByHighestValue(user.getGenres()).entrySet();

        Iterator<Entry<String, Integer>> i = set.iterator();
        List<Movie> movies = new ArrayList<>();
        int amountOfRecommendations = 3;
        int currentValue = -1;
        while (i.hasNext() && amountOfRecommendations > 0) {
            Entry<String, Integer> mp = i.next();
            if (mp.getValue() != currentValue) {
                if (currentValue != -1) {
                    amountOfRecommendations--;
                }
                currentValue = mp.getValue();
            }

            movies.addAll(movieRepository.findTitlesByGenre((mp.getKey()), amountOfRecommendations));

        }

        return compileMovies(movies);
    }

    public <K, V extends Comparable<V>> Map<K, V> sortByHighestValue(final Map<K, V> map) {
        Comparator<K> valueComparator = (k1, k2) -> {
            int comp = map.get(k2).compareTo(map.get(k1));
            if (comp == 0) return 1;
            else return comp;
        };
        Map<K, V> sortedMap = new TreeMap<>(valueComparator);
        sortedMap.putAll(map);
        return sortedMap;
    }

    private void storeGenreData(User user, List<Movie> movies) {
        final String EMPTY_GENRE = "\\N";
        final int ADD_ONE = 1;
        movies.stream().flatMap(movie -> movie.getGenres().stream()).filter(g -> !g.startsWith(EMPTY_GENRE)).forEach(genre -> {
            user.getGenres().merge(genre, ADD_ONE, Integer::sum);
        });
        userRepository.save(user);
    }


    public List<Cast> getCast(String tconst) {
        return castRepository.findByTconst(tconst);
    }

    public List<Name> getActors(List<Cast> casts) {
        List<Name> actors = new ArrayList<>();
        for (Cast cast : casts) {
            Name actor = nameRepository.findByNconst(cast.getNconst());
            if (actor != null) actors.add(actor);
        }
        return actors;
    }
}
