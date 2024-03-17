package it.epicode.ProgettoCapstone.security;

import it.epicode.ProgettoCapstone.entities.User;
import it.epicode.ProgettoCapstone.exceptions.UnauthorizedException;
import it.epicode.ProgettoCapstone.services.UserSRV;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private it.epicode.ProgettoCapstone.security.JWTTools jwtTools;
    @Autowired
    private UserSRV userSRV;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer"))
            throw new UnauthorizedException("Please insert a valid token in header");

        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);

        String userId = jwtTools.extractIdFromToken(accessToken);

        User found = userSRV.getUserById(UUID.fromString(userId));

        Authentication authentication = new UsernamePasswordAuthenticationToken(found, null, found.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher.match("/auth/**", request.getServletPath())
                || antPathMatcher.match("/v3/api-docs/**", request.getServletPath())
                || antPathMatcher.match("/swagger-ui/**", request.getServletPath())
                || antPathMatcher.match("/works", request.getServletPath()) && request.getMethod().equals("GET")
                || antPathMatcher.match("/works/**", request.getServletPath()) && request.getMethod().equals("GET")
                || antPathMatcher.match("/comments", request.getServletPath()) && request.getMethod().equals("GET")
                || antPathMatcher.match("/comments/**", request.getServletPath()) && request.getMethod().equals("GET");
    }
}
