package org.rbnk.api.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.rbnk.api.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            if (jwt.isBlank()) {
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpServletResponse.getWriter().write("Invalid JWT Token");
                httpServletResponse.getWriter().flush();
                httpServletResponse.getWriter().close();
                return;
            }
            try {
                String role = jwtUtil.validateTokenAndGetClaim(jwt);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(role, null,
                                List.of(new SimpleGrantedAuthority(role)));
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (JWTVerificationException e) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.getWriter().write("Invalid or expired JWT Token");
                httpServletResponse.getWriter().flush();
                httpServletResponse.getWriter().close();
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}