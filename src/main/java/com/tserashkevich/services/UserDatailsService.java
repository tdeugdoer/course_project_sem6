package com.tserashkevich.services;

import com.tserashkevich.models.User;
import com.tserashkevich.repositories.UserRepository;
import com.tserashkevich.security.UserDatails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDatailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(s);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        return new UserDatails(user.get());
    }
}
