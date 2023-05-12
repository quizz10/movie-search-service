package com.example.moviesearchservice.service;

import com.example.moviesearchservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final UserService userService;

    public List<Map.Entry<String, Integer>> findTopThreeGenres(long userId) {
        User user = userService.findUserById(userId);
        return sortByHighestValue(user.getGenres());
    }

    private <K, V extends Comparable<V>> List<Map.Entry<K, V>> sortByHighestValue(final Map<K, V> map) {
        Comparator<K> valueComparator = (k1, k2) -> map.get(k2).compareTo(map.get(k1));
        Map<K, V> sortedMap = new TreeMap<>(valueComparator);
        sortedMap.putAll(map);
        return sortedMap.entrySet().stream().limit(3).collect(Collectors.toList());
    }
}
