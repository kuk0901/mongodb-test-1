package com.exam.mongo.test.mongo_test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.exam.mongo.test.mongo_test.security.CustomAccessDeniedHandler;
import com.exam.mongo.test.mongo_test.security.CustomAuthenticationEntryPoint;

import com.exam.mongo.test.mongo_test.token.TokenAuthenticationFilter;
import com.exam.mongo.test.mongo_test.token.TokenService;

import com.exam.mongo.test.mongo_test.user.repository.UserRepository;
import com.exam.mongo.test.mongo_test.user.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final TokenService tokenService;
  private final UserRepository userRepository;
  private final UserService userService;

  public SecurityConfig(TokenService tokenService, UserRepository userRepository, @Lazy UserService userService) {
    this.tokenService = tokenService;
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/signin", "/api/auth/signup", "/api/auth/signout").permitAll()
            .requestMatchers("/api/auth/me", "/api/auth/refresh", "/api/menus/**", "/api/cs/**", "/api/seats/**")
            .authenticated()
            .requestMatchers("/api/admin/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
            .requestMatchers("/api/super-admin/**").hasAuthority("ROLE_SUPER_ADMIN")
            .anyRequest().authenticated())
        .exceptionHandling(exceptions -> exceptions
            .authenticationEntryPoint(customAuthenticationEntryPoint())
            .accessDeniedHandler(customAccessDeniedHandler()));

    return http.build();
  }

  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter(tokenService, userRepository, userService);
  }

  @Bean
  public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
    return new CustomAuthenticationEntryPoint();
  }

  @Bean
  public CustomAccessDeniedHandler customAccessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
