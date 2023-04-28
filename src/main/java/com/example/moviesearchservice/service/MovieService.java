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
    private final RatingRepository ratingRepository;
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

    public List<Movie> findByOriginalTitle(long userId, String originalTitle) {
        User user = userRepository.findByUserId(userId);
        List<Movie> movies = movieRepository.findByOriginalTitleStartsWith(TITLE_REGEX + originalTitle);
        for (Movie movie : movies) {
            if (ratingRepository.findByTconst(movie.getTconst()) != null) {
                movie.setAverageRating(ratingRepository.findByTconst(movie.getTconst()).getAverageRating());
                movie.setCasts(castRepository.findByTconst(movie.getTconst()));
                for (Cast cast : movie.getCasts()) {
                    Name name = nameRepository.findByNconst(cast.getNconst());
                    if (name != null) {
                        movie.getNames().add(name);
                    }
                }
            }
        }
        storeGenreData(user, movies);

        return movies;
    }

    public List<Movie> findByTitle(String title) {
        return movieRepository.findByOriginalTitleStartsWith(TITLE_REGEX + title);
    }

    public List<Movie> findRecommendedTitlesByGenres(long userId) {
        User user = userRepository.findByUserId(userId);

        Set<Entry<String, Integer>> set = valueSort(user.getGenres()).entrySet();

        Iterator<Entry<String, Integer>> i = set.iterator();
        List<Movie> recommendedMovies = new ArrayList<>();
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

            recommendedMovies.addAll(movieRepository.findTitlesByGenre((mp.getKey()), amountOfRecommendations));

        }
        //List<Movie> recommendedMovies = sortedMap.keySet().stream()
        //      .flatMap(genre -> movieRepository.findTwoTitlesByGenre(genre).stream())
        //    .collect(Collectors.toList());

        return recommendedMovies;
    }

    public <K, V extends Comparable<V>> Map<K, V> valueSort(final Map<K, V> map) {
        Comparator<K> valueComparator = (k1, k2) -> {
            int comp = map.get(k2).compareTo(map.get(k1));
            if (comp == 0)
                return 1;
            else
                return comp;
        };
        Map<K, V> sortedMap = new TreeMap<>(valueComparator);
        sortedMap.putAll(map);
        return sortedMap;
    }

    private void storeGenreData(User user, List<Movie> movies) {
        final String EMPTY_GENRE = "\\N";
        final int ADD_ONE = 1;
        movies.stream()
                .flatMap(movie -> movie.getGenres().stream())
                .filter(g -> !g.startsWith(EMPTY_GENRE))
                .forEach(genre -> {
                    user.getGenres().merge(genre, ADD_ONE, Integer::sum);
                });
        userRepository.save(user);
    }


}
