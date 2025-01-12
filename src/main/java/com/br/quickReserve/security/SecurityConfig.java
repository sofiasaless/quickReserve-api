package com.br.quickReserve.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        // primeiro passo é desabilitar o csrf
        http.csrf(csrf -> csrf.disable())
            // passando as rotas publicas
            .authorizeHttpRequests(auth -> {

                auth.requestMatchers("/cliente/**").permitAll();
                auth.requestMatchers( "/restaurante/**").permitAll();
                auth.requestMatchers( "/entrar/**").permitAll();

                auth.anyRequest().authenticated();
            })        
        ;
        
        
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
