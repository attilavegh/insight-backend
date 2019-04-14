package hu.vattila.insight.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import hu.vattila.insight.authentication.GoogleAuthenticationVerifier;
import hu.vattila.insight.entity.Account;
import hu.vattila.insight.entity.SocialUser;
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

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @ExceptionHandler({ Exception.class })
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

            String userId = GoogleAuthenticationVerifier.extractGoogleId(token);
            accounts.removeIf(account -> account.getGoogleId().equals(userId));

            return ResponseEntity.ok(accounts);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Account> getAccount(@PathVariable String id,
                                              @RequestHeader("Authorization") String token) throws GeneralSecurityException, IOException {
        String authorizedGoogleId = GoogleAuthenticationVerifier.extractGoogleId(token);
        if (!authorizedGoogleId.equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Account> optionalAccount = accountRepository.findByGoogleId(id);
        return optionalAccount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody SocialUser socialUser) throws GeneralSecurityException, IOException {
        GoogleIdToken token = GoogleAuthenticationVerifier.validateToken(socialUser.getIdToken());

        if (token == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Account> optionalAccount = accountRepository.findByGoogleId(socialUser.getId());

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            account.setImageUrl(socialUser.getPhotoUrl());
            accountRepository.save(account);

            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.ok(accountRepository.save(GoogleAuthenticationVerifier.createAccount(token)));
        }
    }
}
