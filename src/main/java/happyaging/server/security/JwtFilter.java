package happyaging.server.security;

import happyaging.server.domain.user.User;
import happyaging.server.repository.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_DELIMITER = " ";
    private static final int TOKEN_INDEX = 1;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isValidHeader(authorization)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(TOKEN_DELIMITER)[TOKEN_INDEX];
        if (JwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = JwtUtil.getUserIdFromToken(token);
        if (isInvalidUser(userId)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    private boolean isInvalidUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user == null;
    }

    private static boolean isValidHeader(String authorization) {
        return authorization == null || !authorization.startsWith(TOKEN_PREFIX);
    }
}
