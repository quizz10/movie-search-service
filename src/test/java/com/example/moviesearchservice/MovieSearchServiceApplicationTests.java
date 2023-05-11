package com.example.moviesearchservice;


import com.example.moviesearchservice.model.Movie;
import com.example.moviesearchservice.model.User;
import com.example.moviesearchservice.repository.UserRepository;
import com.example.moviesearchservice.service.MovieService;
import com.example.moviesearchservice.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class MovieSearchServiceApplicationTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieService movieService;

    @Test
    @DisplayName("Find user should give ID 17")
    public void findUser() {
        User user = findUserById(17);
        Long userId = user.getUserId();
        assertEquals(17, userId);
    }

    @Test
    @DisplayName("Find recommended genres should give 6 recommendations")
    public void findRecommendedGenres(){
        User user = findUserById(17);

        List<Movie> genres =  movieService.findRecommendedTitlesByTopGenresAndUserId(user.getUserId());
        int amountOfRecommendations = 6;
        assertEquals(amountOfRecommendations, genres.size());
    }

    @Test
    @DisplayName("Searching for a title should add genre and score to user")
    public void findByTitleAndUserId() {
        User user = findUserById(17);
        int genreScoreBefore = user.getGenres().get("Mystery");

        movieService.findByOriginalTitleAndUserId(17, "Se7en");

        int genreScoreAfter = findUserById(user.getUserId()).getGenres().get("Mystery");

        assertEquals(genreScoreAfter, genreScoreBefore+1);
    }

    private User findUserById(long userId) {
        return userService.findUserById(userId);
    }
}

