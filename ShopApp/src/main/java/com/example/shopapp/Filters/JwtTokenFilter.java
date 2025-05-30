package com.example.shopapp.Filters;

import com.example.shopapp.Component.JWTTokenUtil;
import com.example.shopapp.Model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("/${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JWTTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
         if(isBypassToken(request)) {
             filterChain.doFilter(request, response);
         }
        final String authHeader = request.getHeader("Authorization");
         if(authHeader != null && authHeader.startsWith("Bearer ")) {
             final String token = authHeader.substring(7);
             final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
//             if(phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                 User existingUser =  userDetailsService.loadUserByUsername(phoneNumber);
//
//             }
         }
    }



    private boolean isBypassToken(@NotNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/products", apiPrefix), "GET"),
                Pair.of(String.format("%s/categories", apiPrefix), "GET")
        );
        for(Pair<String, String> bypassToken : bypassTokens) {
            if(request.getServletPath().contains(bypassToken.getFirst())
                    && request.getMethod().equals(bypassToken.getSecond())) {
                return true;
            }
        }
        return false;
    }
}
