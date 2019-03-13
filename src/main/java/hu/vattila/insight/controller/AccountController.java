package hu.vattila.insight.controller;

import hu.vattila.insight.entity.Account;
import hu.vattila.insight.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<List<Account>> searchAccounts(@RequestParam String fragment) {
        List<Account> users = userRepository.findAllByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithIgnoreCase(fragment, fragment);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Optional<Account> accountOptional = userRepository.findByEmail(account.getEmail());

        if (accountOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(userRepository.save(account));
    }
}
