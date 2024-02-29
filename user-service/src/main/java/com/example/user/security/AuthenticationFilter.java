package com.example.user.security;

import com.example.user.dto.TokenDTO;
import com.example.user.dto.UserRequestDTO;
import com.example.user.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;

@Slf4j
@NoArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private LoginService loginService;
    private TokenProvider tokenProvider;

    private UserRequestDTO requestDTO;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private String EXPIRATION;

    public AuthenticationFilter(LoginService loginService, TokenProvider tokenProvider) {
        this.loginService = loginService;
        this.tokenProvider = tokenProvider;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            log.info("jwt filter is running..");
            requestDTO = new ObjectMapper().readValue(request.getInputStream(), UserRequestDTO.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDTO.getMail(),
                            requestDTO.getPassword(),
                            null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        TokenDTO tokenDTO = loginService.login(requestDTO);
        tokenProvider.accessTokenSetHeader(tokenDTO.getAccessToken(), response);
        tokenProvider.refreshTokenSetHeader(tokenDTO.getRefreshToken(), response);
    }
}