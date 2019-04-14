package hu.vattila.insight.controller;

import hu.vattila.insight.authentication.GoogleAuthenticationVerifier;
import hu.vattila.insight.entity.Account;
import hu.vattila.insight.entity.Insight;
import hu.vattila.insight.repository.AccountRepository;
import hu.vattila.insight.repository.InsightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"https://elte-insight.firebaseapp.com", "http://localhost:4200"})
@RestController
@RequestMapping("/api/insight")
public class InsightController {

    private final InsightRepository insightRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public InsightController(InsightRepository insightRepository, AccountRepository accountRepository) {
        this.insightRepository = insightRepository;
        this.accountRepository = accountRepository;
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleException(Exception exception) {
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/sent/{senderId}")
    public ResponseEntity<List<Insight>> getSentInsights(@PathVariable("senderId") Integer senderId,
                                                         @RequestHeader("Authorization") String token) throws GeneralSecurityException, IOException {
        String authorizedGoogleId = GoogleAuthenticationVerifier.extractGoogleId(token);
        Optional<Account> optionalAccount = accountRepository.findByGoogleId(authorizedGoogleId);

        if (optionalAccount.isPresent()) {
            Integer accountId = optionalAccount.get().getId();

            if (accountId.equals(senderId)) {
                List<Insight> insights = insightRepository.findAllBySenderIdOrderByDateDesc(senderId);
                return ResponseEntity.ok(insights);
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/received/{receiverId}")
    public ResponseEntity<List<Insight>> getReceivedInsights(@PathVariable("receiverId") Integer receiverId,
                                                             @RequestHeader("Authorization") String token) throws GeneralSecurityException, IOException {
        String authorizedGoogleId = GoogleAuthenticationVerifier.extractGoogleId(token);
        Optional<Account> optionalAccount = accountRepository.findByGoogleId(authorizedGoogleId);

        if (optionalAccount.isPresent()) {
            Integer accountId = optionalAccount.get().getId();

            if (accountId.equals(receiverId)) {
                List<Insight> insights = insightRepository.findAllByReceiverIdOrderByDateDesc(receiverId);
                return ResponseEntity.ok(insights);
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/send")
    public ResponseEntity<Insight> saveInsight(@RequestBody Insight insight) {
        return ResponseEntity.ok(insightRepository.save(insight));
    }
}
