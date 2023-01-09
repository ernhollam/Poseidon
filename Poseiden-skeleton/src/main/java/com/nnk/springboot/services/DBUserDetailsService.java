package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class DBUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DBUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) throw new UsernameNotFoundException("User " + username + " does not exist");

        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(), user.get().getPassword(), true, true,
                true, true,
                Collections.singletonList(user.get().getRole().equalsIgnoreCase("ADMIN") ?
                                          new SimpleGrantedAuthority("ROLE_ADMIN") :
                                          new SimpleGrantedAuthority( "ROLE_USER")));
    }
}