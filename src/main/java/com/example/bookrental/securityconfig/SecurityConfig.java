package com.example.bookrental.securityconfig;

import com.example.bookrental.exception.CustomMessageSource;
import com.example.bookrental.filter.JwtAuthFilter;
import com.example.bookrental.repo.UserEntityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserEntityRepo userEntityRepo;
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthEntryPoint point;
    private CustomMessageSource messageSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoDetailService(userEntityRepo);
    }

    private static final String[] SWAGGER_URLS = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    // Filter chain for configuration of authentication
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/user/add-user", "/admin/user/deactivate").hasRole("ADMIN")
                        .requestMatchers("/lib/**", "/admin/user/reset").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(SWAGGER_URLS).permitAll()
                        .requestMatchers("/reset-password/generate-Otp","reset-password/reset","/admin/user/refreshToken").permitAll()
                        .requestMatchers("/admin/user/login").permitAll().anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(e->e.authenticationEntryPoint(point))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    // Password encoder
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }




}

