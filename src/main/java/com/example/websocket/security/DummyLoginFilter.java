package com.example.websocket.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class DummyLoginFilter extends OncePerRequestFilter {

    private final DummyUserConfig dummyUserConfig;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // Login with dummy user
        SecurityContext dummyLogin = SecurityContextHolder.createEmptyContext();
        dummyLogin.setAuthentication( new DummyAuthentication( dummyUserConfig.getUser() ) );
        SecurityContextHolder.setContext( dummyLogin );

        filterChain.doFilter( request, response );
    }
}
