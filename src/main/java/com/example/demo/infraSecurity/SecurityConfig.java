package com.example.demo.infraSecurity;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    JwtAuthFilter filtro;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "barbearia/cadastrar").permitAll()
                        .requestMatchers(HttpMethod.GET, "barbearia/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "cliente/cadastrar").permitAll()
                        .requestMatchers("barbeiro/**").hasRole("BARBEARIA")
                        .requestMatchers("servico/**").hasRole("BARBEARIA")
                        .requestMatchers("contato/**").hasRole("BARBEARIA")
                        .requestMatchers("endereco/**").hasRole("BARBEARIA")

                        .requestMatchers(HttpMethod.POST, "auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filtro, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
