package com.edimilsonborges.forum_hub.security;

import com.edimilsonborges.forum_hub.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString()) && (requestURI.equalsIgnoreCase("/auth") || requestURI.equalsIgnoreCase("/usuarios"))) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenJWT = recuperarToken(request);
        if (!isTokenFornecido(tokenJWT, response)) {
            return;
        }

        String subject = tokenService.getSubject(tokenJWT);
        if (!isSubjectExistente(subject, response)) {
            return;
        }

        UserDetails usuarioDetails = usuarioRepository.findByEmail(subject);
        if(!verificaUsuarioExiste(usuarioDetails, response)){
          return;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioDetails, null, usuarioDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private boolean verificaUsuarioExiste(UserDetails userDetails, HttpServletResponse response) throws IOException{
        if(userDetails == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\": \"Este token não é mais válido, gere-o novamente!\"}");
            return false;
        }
        return true;
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean isTokenFornecido(String tokenJWT, HttpServletResponse response) throws IOException {
        if (tokenJWT == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\": \"Token JWT não fornecido!\"}");
            return false;
        }
        return true;
    }

    private boolean isSubjectExistente(String subject, HttpServletResponse response) throws IOException {
        if (subject == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\": \"Token JWT inválido ou expirado!\"}");
            return false;
        }
        return true;
    }
}
