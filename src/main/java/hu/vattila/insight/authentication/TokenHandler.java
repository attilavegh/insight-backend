package hu.vattila.insight.authentication;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import hu.vattila.insight.model.OneTimeAuthCode;

import java.io.IOException;
import java.util.Collections;

public class TokenHandler {

    public static GoogleIdToken verifyToken(String token) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(AuthConstants.CLIENT_ID.getValue()))
                .build();

        try {
            String parsedToken = AuthUtils.parseToken(token);
            return verifier.verify(parsedToken);
        } catch (Exception e) {
            return null;
        }
    }

    public static GoogleTokenResponse exchangeAuthorizationCode(OneTimeAuthCode authCode) throws IOException {
        return new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                AuthConstants.TOKEN_SERVER_URL.getValue(),
                AuthConstants.CLIENT_ID.getValue(),
                AuthConstants.CLIENT_SECRET.getValue(),
                authCode.getCode(),
                AuthConstants.DEV_REDIRECT_URI.getValue()
        ).execute();
    }

    public static GoogleTokenResponse refreshToken(String refreshToken) {
        try {
            return new GoogleRefreshTokenRequest(
                    new NetHttpTransport(),
                    new JacksonFactory(),
                    refreshToken,
                    AuthConstants.CLIENT_ID.getValue(),
                    AuthConstants.CLIENT_SECRET.getValue()
            ).execute();
        } catch (IOException e) {
            return null;
        }
    }
}
