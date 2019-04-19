package hu.vattila.insight.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import hu.vattila.insight.authentication.AuthUtils;
import hu.vattila.insight.authentication.TokenHandlerService;
import hu.vattila.insight.entity.Account;
import hu.vattila.insight.model.OneTimeAuthCode;
import hu.vattila.insight.model.Token;
import hu.vattila.insight.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"https://elte-insight.firebaseapp.com", "http://localhost:4200"})
@RestController
@RequestMapping("/api/user")
public class AccountController {

    private final AccountRepository accountRepository;
    private final TokenHandlerService tokenHandlerService;

    @Autowired
    public AccountController(AccountRepository accountRepository, TokenHandlerService tokenHandlerService) {
        this.accountRepository = accountRepository;
        this.tokenHandlerService = tokenHandlerService;
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleException(Exception exception) {
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public ResponseEntity<List<Account>> searchAccount(@RequestParam String fragment,
                                                       @RequestHeader("Authorization") String token) throws GeneralSecurityException, IOException {
        if (fragment.equals("")) {
            return ResponseEntity.ok(Collections.emptyList());
        } else {
            List<Account> accounts = accountRepository.findAllByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithIgnoreCase(fragment, fragment);

            String userId = AuthUtils.extractGoogleId(token);
            accounts.removeIf(account -> account.getGoogleId().equals(userId));

            return ResponseEntity.ok(accounts);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Account> getAccount(@PathVariable String id,
                                              @RequestHeader("Authorization") String token) throws GeneralSecurityException, IOException {
        String authorizedGoogleId = AuthUtils.extractGoogleId(token);
        if (!authorizedGoogleId.equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Account> optionalAccount = accountRepository.findByGoogleId(id);
        return optionalAccount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody OneTimeAuthCode authCode) throws IOException {

        if (authCode == null) {
            return ResponseEntity.badRequest().build();
        }

        GoogleTokenResponse tokenResponse = tokenHandlerService.exchangeAuthorizationCode(authCode);

        GoogleIdToken parsedIdToken = tokenResponse.parseIdToken();
        String googleId = parsedIdToken.getPayload().getSubject();
        String imageUrl = parsedIdToken.getPayload().get("picture").toString();

        Optional<Account> existingOptionalAccount = accountRepository.findByGoogleId(googleId);

        if (existingOptionalAccount.isPresent()) {
            Account existingAccount = existingOptionalAccount.get();
            existingAccount.setImageUrl(imageUrl);

            accountRepository.save(existingAccount);
        } else {
            Account newAccount = AuthUtils.createAccount(tokenResponse.parseIdToken());
            accountRepository.save(newAccount);
        }

        String refreshToken = tokenResponse.getRefreshToken();
        String idToken = tokenResponse.getIdToken();

        return ResponseEntity.ok(new Token(idToken, refreshToken));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Token> login(@RequestBody Token token) {

        if (token == null || token.getRefreshToken() == null) {
            return ResponseEntity.badRequest().build();
        }

        String refreshedToken = getRefreshedAuthToken(token.getRefreshToken());
        return refreshedToken != null ? ResponseEntity.ok(new Token(refreshedToken, token.getRefreshToken())) : ResponseEntity.badRequest().build();
    }

    private String getRefreshedAuthToken(String token) {
        GoogleTokenResponse tokenResponse = TokenHandlerService.refreshToken(token);
        return tokenResponse != null ? tokenResponse.getIdToken() : null;
    }

}
