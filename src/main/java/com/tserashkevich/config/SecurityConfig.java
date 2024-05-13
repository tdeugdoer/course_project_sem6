package com.tserashkevich.config;

import com.tserashkevich.services.UserDatailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@EnableWebSecurity
public class SecurityConfig {
    private final UserDatailsService userDatailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDatailsService)
                .authorizeHttpRequests(requests -> requests
//                                .requestMatchers("/item/new").hasRole("ADMIN")
//                                .requestMatchers("/user/**").hasRole("USER")
//                                .requestMatchers("/auth/**", "/catalog", "/item/info/**").permitAll()
                                .anyRequest().permitAll()
//                        .requestMatchers("/auth/login", "/error",
//                                "/auth/registration", "/logout", "/catalog", "/item/info/**").permitAll()
//                        .anyRequest().hasRole("ADMIN")
//                        .requestMatchers("/user/**").authenticated()

                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/catalog", true)
                        .failureUrl("/auth/login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login"));
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
