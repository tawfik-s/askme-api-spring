package com.tawfeek.askme.repository;

import com.tawfeek.askme.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

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
    void findByUserName() {
        User user=new User();
        user.setEmail("t.shalash1@gmail.com");
        user.setPassword("1234");
        user.setUserName("tawfeek");
        userRepository.save(user);
        User result = userRepository.findByUserName("tawfeek");
        assertThat(Objects.equals(result.getEmail(), "t.shalash1@gmail.com")&&result.getUsername().equals("tawfeek"));
    }
}