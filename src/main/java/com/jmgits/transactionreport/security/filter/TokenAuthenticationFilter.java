package com.jmgits.transactionreport.security.filter;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.jmgits.transactionreport.security.resolver.TokenHandler;
import com.jmgits.transactionreport.security.utils.SecurityUtils;
import com.jmgits.transactionreport.security.view.TokenData;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenHandler tokenHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authorization = request.getHeader(AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        var token = authorization.replaceAll("Bearer ", "");

        try {

            var userId = tokenHandler.getSubjectFromToken(token);

            SecurityUtils.setTokenData(new TokenData(userId));

        } catch (Exception e) {

            log.warn("Error checking authorization.", e);

            generateResponse(response);

            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return SecurityUtils.findTokenData().isPresent();
    }

    //
    // private

    private void generateResponse(HttpServletResponse response) throws IOException {
        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getOutputStream().println("{\"errorCode\": \"UNAUTHORIZED\", \"errorDescription\": \"Invalid authorization.\"}");
    }
}
