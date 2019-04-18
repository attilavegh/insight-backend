package hu.vattila.insight.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import hu.vattila.insight.entity.Account;

import java.security.GeneralSecurityException;

public class AuthUtils {

    public static Account createAccount(GoogleIdToken idToken) {
        GoogleIdToken.Payload payload = idToken.getPayload();

        Account account = new Account();
        account.setGoogleId(payload.getSubject());
        account.setFirstName(payload.get("given_name").toString());
        account.setLastName(payload.get("family_name").toString());
        account.setFullName(payload.get("name").toString());
        account.setEmail(payload.getEmail());
        account.setImageUrl(payload.get("picture").toString());

        return account;
    }

    public static String extractGoogleId(String token) throws GeneralSecurityException {
        GoogleIdToken validatedToken = TokenHandler.verifyToken(token);

        if (validatedToken != null) {
            return validatedToken.getPayload().getSubject();
        } else {
            throw new GeneralSecurityException();
        }
    }

    public static boolean isAuthToken(String token) {
        return token.startsWith(AuthConstants.TOKEN_PREFIX.getValue());
    }

    public static String parseToken(String token) {
        return token.replace(AuthConstants.TOKEN_PREFIX.getValue(), "");
    }
}
