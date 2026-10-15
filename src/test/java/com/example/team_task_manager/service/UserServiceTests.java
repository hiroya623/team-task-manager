
package com.example.team_task_manager.service;

import com.example.team_task_manager.dto.UserCreateRequest;
import com.example.team_task_manager.dto.UserResponse;
import com.example.team_task_manager.entity.User;
import com.example.team_task_manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void ユーザー一覧を取得できる() {
        User user = new User();
        user.setUsername("テストユーザー");
        user.setEmail("test@example.com");

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponse> result = userService.findAll();

        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void IDでユーザーを取得できる() {
        User user = new User();
        user.setUsername("テストユーザー");
        user.setEmail("test@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse result = userService.findById(1L);

        assertNotNull(result);
        verify(userRepository).findById(1L);
    }

    @Test
    void 存在しないIDでは404エラーになる() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.findById(999L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void パスワードをハッシュ化してユーザーを登録できる() {
        UserCreateRequest request = new UserCreateRequest();
        request.setUsername("テストユーザー");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        when(userRepository.existsByEmail("test@example.com"))
                .thenReturn(false);
        when(passwordEncoder.encode("password123"))
                .thenReturn("hashed-password");
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse result = userService.create(request);

        assertNotNull(result);
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(argThat(user ->
                "hashed-password".equals(user.getPassword())
        ));
    }

    @Test
    void 登録済みのメールアドレスでは409エラーになる() {
        UserCreateRequest request = new UserCreateRequest();
        request.setUsername("テストユーザー");
        request.setEmail("existing@example.com");
        request.setPassword("password123");

        when(userRepository.existsByEmail("existing@example.com"))
                .thenReturn(true);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.create(request)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }
}