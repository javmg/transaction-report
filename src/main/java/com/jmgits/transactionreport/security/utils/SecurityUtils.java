package com.jmgits.transactionreport.security.utils;

import com.jmgits.transactionreport.security.view.TokenData;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static void setTokenData(TokenData tokenData) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(tokenData.getUserId(), null, List.of())
        );
    }

    public static Optional<TokenData> findTokenData() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return Optional.empty();
        }

        var tokenData = new TokenData(authentication.getName());
        return Optional.of(tokenData);
    }

    public static TokenData getTokenData() {
        return findTokenData().orElseThrow(() ->
                new AuthenticationCredentialsNotFoundException("No authentication found.")
        );
    }

    public static String getUserId() {
        return getTokenData().getUserId();
    }
}
