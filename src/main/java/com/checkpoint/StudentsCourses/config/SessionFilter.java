package com.checkpoint.StudentsCourses.config;

import com.checkpoint.StudentsCourses.service.SessionManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SessionFilter extends OncePerRequestFilter {
    private final SessionManager sessionManager;
    private final UserDetailsService userDetailsService;

    public SessionFilter(@Lazy SessionManager sessionManager,@Lazy UserDetailsService userDetailsService) {
        this.sessionManager = sessionManager;
        this.userDetailsService = userDetailsService;

    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String sessionKey = request.getHeader("Session-Key");
//
//        if (sessionKey != null && sessionManager.isValidSession(sessionKey)) {
//            String username = sessionManager.getUsername(sessionKey);
//
//            // Create Authentication object
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(username, null, List.of());
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }

//@Override
//protected void doFilterInternal(HttpServletRequest request,
//                                HttpServletResponse response,
//                                FilterChain filterChain) throws ServletException, IOException {
//
//    String sessionKey = request.getHeader("Session-Key");
//
//    if (sessionKey != null && sessionManager.isValidSession(sessionKey)) {
//        String username = sessionManager.getUsername(sessionKey);
//
//        // Fetch roles/authorities
//        List<String> roles = sessionManager.getRoles(sessionKey); // You must implement this
//
//        List<GrantedAuthority> authorities = roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .toList();
//
//        UsernamePasswordAuthenticationToken authentication =
//                new UsernamePasswordAuthenticationToken(username, null, authorities);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//    filterChain.doFilter(request, response);
//}

//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String sessionKey = request.getHeader("Session-Key");
//
//        if (sessionKey != null && sessionManager.isValidSession(sessionKey)) {
//            String username = sessionManager.getUsername(sessionKey);
//
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(username, null, List.of());
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String sessionKey = request.getHeader("Session-Key");

        if (sessionKey != null && sessionManager.isValidSession(sessionKey)) {
            String username = sessionManager.getUsername(sessionKey);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
