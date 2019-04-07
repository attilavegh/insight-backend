package hu.vattila.insight.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import hu.vattila.insight.entity.Account;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleAuthenticationVerifier {
    public static GoogleIdToken validateToken(String token) throws GeneralSecurityException, IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList("***REMOVED***"))
                .build();

        String parsedToken = parseToken(token);
        return verifier.verify(parsedToken);
    }

    public static Account createAccount(GoogleIdToken token) {
        GoogleIdToken.Payload payload = token.getPayload();

        Account account = new Account();
        account.setGoogleId(payload.getSubject());
        account.setFirstName(payload.get("given_name").toString());
        account.setLastName(payload.get("family_name").toString());
        account.setFullName(payload.get("name").toString());
        account.setEmail(payload.getEmail());
        account.setImageUrl(payload.get("picture").toString());

        return account;
    }

    public static String extractGoogleId(String token) throws GeneralSecurityException, IOException {
        GoogleIdToken validatedToken = validateToken(token);

        if (validatedToken != null) {
            return validatedToken.getPayload().getSubject();
        } else {
            throw new GeneralSecurityException();
        }
    }

    private static String parseToken(String token) {
        return token.replace(SecurityConstants.TOKEN_PREFIX.getValue(), "");
    }
}
