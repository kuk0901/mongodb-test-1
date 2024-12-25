// package com.exam.mongo.test.mongo_test.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.exam.mongo.test.mongo_test.token.TokenAuthenticationFilter;
// import com.exam.mongo.test.mongo_test.token.TokenService;
// import com.exam.mongo.test.mongo_test.user.repository.UserRepository;
// import com.exam.mongo.test.mongo_test.user.service.UserService;

// import jakarta.servlet.http.HttpServletResponse;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//   private final TokenService tokenService;
//   private final UserRepository userRepository;
//   private final UserService userService;

//   public SecurityConfig(@Lazy TokenService tokenService, @Lazy UserRepository userRepository,
//       @Lazy UserService userService) {
//     this.tokenService = tokenService;
//     this.userRepository = userRepository;
//     this.userService = userService;
//   }

//   @Bean
//   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//     http
//         .csrf(csrf -> csrf.disable())
//         .addFilterBefore(new TokenAuthenticationFilter(tokenService, userRepository, userService),
//             UsernamePasswordAuthenticationFilter.class)
//         .authorizeHttpRequests(auth -> auth
//             .requestMatchers("/api/auth/signin", "/api/auth/signup", "/api/auth/signout")
//             .permitAll()
//             .requestMatchers("/api/auth/me", "/api/auth/refresh").authenticated()
//             .requestMatchers("/api/admin/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
//             .requestMatchers("/api/super-admin/**").hasAuthority("ROLE_SUPER_ADMIN")
//             .anyRequest().authenticated())
//         .exceptionHandling(exceptions -> exceptions
//             // 인증되지 않은 요청에 대해 401 에러 반환
//             .authenticationEntryPoint((req, res, authException) -> {
//               res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//             })
//             // 권한이 없는 요청에 대해 403 에러 반환
//             .accessDeniedHandler((req, res, accessDeniedException) -> {
//               res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
//             }));

//     return http.build();
//   }

//   @Bean
//   public PasswordEncoder passwordEncoder() {
//     return new BCryptPasswordEncoder();
//   }
// }

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

import com.exam.mongo.test.mongo_test.token.TokenAuthenticationFilter;
import com.exam.mongo.test.mongo_test.token.TokenService;
import com.exam.mongo.test.mongo_test.user.repository.UserRepository;
import com.exam.mongo.test.mongo_test.user.service.UserService;
import com.exam.mongo.test.security.CustomAccessDeniedHandler;
import com.exam.mongo.test.security.CustomAuthenticationEntryPoint;

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
            .requestMatchers("/api/auth/me", "/api/auth/refresh", "/api/menus/**", "/api/cs/**").authenticated()
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
