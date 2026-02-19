package com.system.miniflix.domain.auth.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


import com.system.miniflix.domain.auth.entity.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 저장 및 조회")
    void saveAndFind() {
        // given
        User user = User.builder()
                .email("test@test.com")
                .password("password123")
                .nickname("테스터")
                .role(User.Role.USER)
                .build();

        // when
        User saved = userRepository.save(user);
        User found = userRepository.findById(saved.getId()).orElseThrow();

        // then
        assertThat(found.getEmail()).isEqualTo("test@test.com");
        assertThat(found.getNickname()).isEqualTo("테스터");
        assertThat(found.getRole()).isEqualTo(User.Role.USER);
    }

    @Test
    @DisplayName("이메일로 유저 조회")
    void findByEmail() {
        // given
        User user = User.builder()
                .email("test@test.com")
                .password("password123")
                .nickname("테스터")
                .role(User.Role.USER)
                .build();
        userRepository.save(user);

        // when
        User found = userRepository.findByEmail("test@test.com").orElseThrow();

        // then
        assertThat(found.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    @DisplayName("이메일 중복 확인")
    void existsByEmail() {
        // given
        User user = User.builder()
                .email("test@test.com")
                .password("password123")
                .nickname("테스터")
                .role(User.Role.USER)
                .build();
        userRepository.save(user);

        // when & then
        assertThat(userRepository.existsByEmail("test@test.com")).isTrue();
        assertThat(userRepository.existsByEmail("none@test.com")).isFalse();
    }
}