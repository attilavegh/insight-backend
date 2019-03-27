package hu.vattila.insight.controller;

import hu.vattila.insight.entity.Account;
import hu.vattila.insight.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://vattila-insight.herokuapp.com", "http://localhost:4200"})
@RestController
@RequestMapping("/api/user")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping()
    public ResponseEntity<List<Account>> searchUsers(@RequestParam String fragment) {
        if (fragment.equals("")) {
            return ResponseEntity.ok(Collections.emptyList());
        } else {
            List<Account> accounts = accountRepository.findAllByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithIgnoreCase(fragment, fragment);
            return ResponseEntity.ok(accounts);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Optional<Account> userOptional = accountRepository.findByGoogleId(account.getGoogleId());

        if (userOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(accountRepository.save(account));
    }
}
