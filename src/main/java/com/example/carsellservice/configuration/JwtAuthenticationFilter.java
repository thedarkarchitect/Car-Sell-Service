package com.example.carsellservice.configuration;

import com.example.carsellservice.service.jwt.UserService;
import com.example.carsellservice.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { //oncePerRequestFilter is a class that ensures te filter is executed once per request

    private final JWTUtil jwtUtil;

    private final UserService userService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); //this retrieves the Authorization header
        final String jwt;
        final String userEmail;

        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) { // this checkes if the header is empty or does not start with  `Bearer`
            filterChain.doFilter(request, response);
            return;//if this true, the conditional will return nothing and passes the request to the next filter without performing any authentication
        }

        jwt = authHeader.substring(7);// this extracts the token
        userEmail = jwtUtil.extractUsername(jwt);//this extracts the userEmail from the token

        if (StringUtils.isNoneEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) { //checks if the extracted username is not empty and the security context does not already have an authentication object
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail); //this loads userDetails from the userService
            if(jwtUtil.isTokenValid(jwt, userDetails)) { //checks if the token is valid
                SecurityContext context = SecurityContextHolder.createEmptyContext(); //created empty security context
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //creates usernamePasswordAuthenticationToken with user details and authorities
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));//sets request details in the authentication token
                context.setAuthentication(usernamePasswordAuthenticationToken);//sets the authentication token in the security context
                SecurityContextHolder.setContext(context);//updates the security context with the new context
            }
        }
        filterChain.doFilter(request, response);//passes the request and response to the next filter in the chain
        //So overRoll this filter method extracts the JWT token from the Authorization header of incomming Http requests, validates it, and sets the authentication in the security context if the token is valid
    }
}
