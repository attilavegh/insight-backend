package hu.vattila.insight.utility;

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

public class Authentication {
    public static GoogleIdToken validateToken(String token) throws GeneralSecurityException, IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList("***REMOVED***"))
                .build();

        return verifier.verify(token);
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
}
