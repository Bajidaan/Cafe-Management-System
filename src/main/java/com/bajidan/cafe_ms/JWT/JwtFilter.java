package com.bajidan.cafe_ms.JWT;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    Claims claims = null;
    private String username = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().matches("/user/signUp|/user/login|/user/password|/user")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizeHeader = request.getHeader("Authorization");
            String token = null;

            if(authorizeHeader != null && authorizeHeader.startsWith("Bearer ")) {
                token = authorizeHeader.substring(7);
                username = jwtUtil.extractUsername(token);
                claims = jwtUtil.extractAllClaims(token);
            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
                if(jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        }

    }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }
    public boolean isUser() {
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser() {
        return username;
    }
}
