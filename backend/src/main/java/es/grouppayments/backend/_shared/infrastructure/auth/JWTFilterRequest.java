package es.grouppayments.backend._shared.infrastructure.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@AllArgsConstructor
public class JWTFilterRequest extends OncePerRequestFilter {
    private final UserDetailsImpl userDetailsImpl;
    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");

        if (isNotAlreadyLogged() && hasJWTHeader(authorizationHeader) && containsNecesaryData(authorizationHeader)) {
            String jwt = authorizationHeader.substring(7);
            UUID userId = jwtUtils.getUserId(jwt);
            UserDetails userDetails = userDetailsImpl.loadUserByUsername(userId.toString());

            if (jwtUtils.isValid(jwt, userId) && !jwtUtils.isExpired(jwt)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }

    private boolean hasJWTHeader(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer");
    }

    private boolean containsNecesaryData(String authorizationHeader) {
        String token = authorizationHeader.substring(7);

        return jwtUtils.containsNecesaryData(token);
    }

    private boolean isNotAlreadyLogged () {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
