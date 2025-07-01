package com.organizaAi.OrganizaAi.infra.filter;

import com.organizaAi.OrganizaAi.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public JwtAuthFilter(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

   @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
        // Deixe que uma exceção aconteça aqui se o token for inválido
        username = jwtService.extractUsername(token); 
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    // ==================  INÍCIO DO DEBUG COM PRINT  ==================
            System.out.println("\n\n"); // Adiciona um espaço para facilitar a leitura no log
            System.out.println("======== [DEBUG JWT FILTER] ========");
            System.out.println("[DEBUG] Usuário carregado: " + userDetails.getUsername());

            if (userDetails.getAuthorities() == null || userDetails.getAuthorities().isEmpty()) {
                System.out.println("[DEBUG] ATENÇÃO: O usuário NÃO possui nenhuma permissão (authorities)!");
            } else {
                System.out.println("[DEBUG] Verificando as permissões do usuário:");
                for (org.springframework.security.core.GrantedAuthority authority : userDetails.getAuthorities()) {
                    // ESTA É A LINHA MAIS IMPORTANTE!
                    // As aspas simples ajudam a ver espaços em branco acidentais.
                    System.out.println("    -> Authority Encontrada: '" + authority.getAuthority() + "'"); 
                }
            }
            System.out.println("======================================");
            System.out.println("\n\n");
            // ==================  FIM DO DEBUG COM PRINT  ===================
            if (jwtService.validateToken(token, userDetails)) { 
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}