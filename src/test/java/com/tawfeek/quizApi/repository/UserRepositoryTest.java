package com.tawfeek.quizApi.repository;

import com.tawfeek.quizApi.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
    @Test
    void shouldReturnUserByName() {
        User user=new User();
        user.setEmail("t.shalash1@gmail.com");
        user.setPassword("1234");
        user.setUserName("tawfeek");
        userRepository.save(user);
        List<User> result = userRepository.findByUserName("tawfeek").get();
        assertThat(Objects.equals(result.get(0).getEmail(), "t.shalash1@gmail.com")&&result.get(0).getUsername().equals("tawfeek"));
        assertThat(result.size()).isEqualTo(1);
    }
}