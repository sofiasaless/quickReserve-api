package com.br.quickReserve.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final SecurityFilterRestaurante securityFilterRestaurante;

    private final SecurityFilterCliente securityFilterCliente;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        // primeiro passo é desabilitar o csrf
        http.csrf(csrf -> csrf.disable())
            // passando as rotas publicas
            .authorizeHttpRequests(auth -> {

                auth.requestMatchers( "/restaurante/**").permitAll();
                auth.requestMatchers(HttpMethod.GET, "/mesas/**");
                auth.requestMatchers("/cliente/**").permitAll();
                auth.requestMatchers( "/entrar/**").permitAll();

                auth.anyRequest().authenticated();
            })        
            
            // criando filtro para as rotas, verificando se o token do usuario permite ele ter acesso a rotas protegidas
            .addFilterBefore(securityFilterRestaurante, BasicAuthenticationFilter.class)
            .addFilterBefore(securityFilterCliente, BasicAuthenticationFilter.class)
        ;
        
        
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
