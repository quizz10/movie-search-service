package com.example.moviesearchservice.service;

import com.example.moviesearchservice.model.User;
import com.example.moviesearchservice.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GenreService {
   private final UserService userService;
// TODO: Plocka bara ut de 3 översta från genres
    // Flytta in all logik som har med genre att göra och returnera det färdiga resultatet.
    public Iterator<Map.Entry<String, Integer>> sortGenresByHighestValue(long userId) {
        User user = userService.findUserById(userId);
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
