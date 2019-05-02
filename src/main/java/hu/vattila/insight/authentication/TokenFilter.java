package hu.vattila.insight.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import hu.vattila.insight.dto.authentication.AuthConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TokenFilter extends BasicAuthenticationFilter {

    TokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String token = req.getHeader(AuthConstants.AUTHORIZATION_HEADER_NAME.getValue());

        if (token == null || !AuthUtils.isAuthToken(token)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken authenticate(String token) {
        return isAuthTokenValid(token) ? grantAccess(token) : null;
    }

    private boolean isAuthTokenValid(String token) {
        GoogleIdToken verifiedToken = TokenHandlerService.verifyToken(token);
        return verifiedToken != null;
    }

    private UsernamePasswordAuthenticationToken grantAccess(String token) {
        return new UsernamePasswordAuthenticationToken(token, null, new ArrayList<>());
    }
}
