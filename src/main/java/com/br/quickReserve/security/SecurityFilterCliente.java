package com.br.quickReserve.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.br.quickReserve.security.providers.JWTProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilterCliente extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // setando primeiramente os headers como nulos
        // SecurityContextHolder.getContext().setAuthentication(null);
        // agora pegando o header de autorização
        String header = request.getHeader("Authorization");

        var prefixUriRequest = request.getRequestURI();

        if (prefixUriRequest.startsWith("/cliente") || prefixUriRequest.startsWith("/auth/cliente")
                || prefixUriRequest.startsWith("/reservas/cliente")) {
            if (header != null) {
                var token = this.jwtProvider.validarToken(header);
                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                // setando o id do cliente no request, assim todas requisições que forem
                // necessárias seu id, serão passadas a partir do subject do token
                request.setAttribute("cliente_id", token.getSubject());

                // mapeando as roles que estão no jwt
                var roles = token.getClaim("roles").asList(Object.class);
                var rolesDeAutorizacao = roles.stream()
                        .map(
                                role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                        .toList();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),
                        null, rolesDeAutorizacao);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);

    }

}
