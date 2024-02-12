//package com.example.user.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    @Autowired
//    private TokenProvider tokenProvider;
//
//    @Autowired
//    RedisTemplate<String, String> redisTemplate;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            String token = parseBearerToken(request);
//            if (token != null && !token.equalsIgnoreCase("null")) {
//                String userId = tokenProvider.validateAndGetUserId(token);
//                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                        userId, null, AuthorityUtils.NO_AUTHORITIES
//                );
//
//                String key = "JWT_TOKEN:" + tokenProvider.validateAndGetUserId(token);
//                String storedToken = (String) redisTemplate.opsForValue().get(key);
//                if (redisTemplate.hasKey(key) && storedToken != null) {
//                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//                    securityContext.setAuthentication(authenticationToken);
//                    SecurityContextHolder.setContext(securityContext);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("could not set user authentication in security context", e);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String parseBearerToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}
