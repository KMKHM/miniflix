package com.system.miniflix.domain.auth.service;

import com.system.miniflix.domain.auth.dto.LoginRequest;
import com.system.miniflix.domain.auth.dto.SignupRequest;
import com.system.miniflix.domain.auth.dto.TokenResponse;
import com.system.miniflix.domain.auth.entity.User;
import com.system.miniflix.domain.auth.repository.UserRepository;
import com.system.miniflix.global.jwt.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() {
        // given
        SignupRequest request = new SignupRequest("test@test.com", "password123", "테스터");
        given(userRepository.existsByEmail(request.getEmail())).willReturn(false);
        given(passwordEncoder.encode(request.getPassword())).willReturn("encodedPassword");

        // when
        authService.signup(request);

        // then
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 중복")
    void signup_fail_duplicate_email() {
        // given
        SignupRequest request = new SignupRequest("test@test.com", "password123", "테스터");
        given(userRepository.existsByEmail(request.getEmail())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용중인 이메일입니다.");
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        // given
        LoginRequest request = new LoginRequest("test@test.com", "password123");
        User user = User.builder()
                .email("test@test.com")
                .password("encodedPassword")
                .nickname("테스터")
                .role(User.Role.USER)
                .build();

        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(request.getPassword(), user.getPassword())).willReturn(true);
        given(jwtProvider.generateToken(user.getEmail(), user.getRole().name())).willReturn("jwt-token");

        // when
        TokenResponse response = authService.login(request);

        // then
        assertThat(response.getAccessToken()).isEqualTo("jwt-token");
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 이메일")
    void login_fail_not_found_email() {
        // given
        LoginRequest request = new LoginRequest("none@test.com", "password123");
        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 이메일입니다.");
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void login_fail_wrong_password() {
        // given
        LoginRequest request = new LoginRequest("test@test.com", "wrongPassword");
        User user = User.builder()
                .email("test@test.com")
                .password("encodedPassword")
                .nickname("테스터")
                .role(User.Role.USER)
                .build();

        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(request.getPassword(), user.getPassword())).willReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}