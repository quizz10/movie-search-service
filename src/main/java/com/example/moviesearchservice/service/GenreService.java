package com.example.moviesearchservice.service;

import com.example.moviesearchservice.model.User;
import com.example.moviesearchservice.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class GenreService {
    MovieRepository movieRepository;
    UserService userService;

    public Iterator<Map.Entry<String, Integer>> sortGenresByHighestValue(long userId) {
        User user = userService.findUserById(userId).get();
        Set<Map.Entry<String, Integer>> set = sortByHighestValue(user.getGenres()).entrySet();

        Iterator<Map.Entry<String, Integer>> iterator = set.iterator();
        return iterator;
    }

    private <K, V extends Comparable<V>> Map<K, V> sortByHighestValue(final Map<K, V> map) {
        Comparator<K> valueComparator = (k1, k2) -> {
            int comp = map.get(k2).compareTo(map.get(k1));
            if (comp == 0) return 1;
            else return comp;
        };
        Map<K, V> sortedMap = new TreeMap<>(valueComparator);
        sortedMap.putAll(map);
        return sortedMap;
    }
}
