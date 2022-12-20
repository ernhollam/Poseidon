package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(DBUserDetailsService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBUserDetailsServiceTest {
    @Autowired
    DBUserDetailsService userDetailsService;

    @MockBean
    UserRepository userRepository;

    @Test
    public void userNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("testName"));
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "username";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new User(1, username, "abc123ABC-+*", "John Doe", "ADMIN")));
        userDetailsService.loadUserByUsername(username);
        verify(userRepository, times(1)).findByUsername(username);
    }
}