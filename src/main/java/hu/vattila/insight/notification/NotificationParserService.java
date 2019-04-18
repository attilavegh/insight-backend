package hu.vattila.insight.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.vattila.insight.entity.Account;
import hu.vattila.insight.entity.Insight;
import hu.vattila.insight.model.Notification;
import hu.vattila.insight.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Transactional
@Service
class NotificationParserService {

    @Autowired
    private AccountRepository accountRepository;

    Insight parse(String payload) {
        Notification notification = parseNotification(payload);
        if (notification == null) {
            return null;
        }

        Insight insight = new Insight();
        insight.setId(notification.getId());
        insight.setContinueMessage(notification.getContinueMessage());
        insight.setConsiderMessage(notification.getConsiderMessage());
        insight.setDate(notification.getDate());

        Account sender = getAccount(notification.getSender());
        Account receiver = getAccount(notification.getReceiver());

        if (sender == null || receiver == null) {
            return null;
        }

        insight.setSender(sender);
        insight.setReceiver(receiver);

        return insight;
    }

    private Notification parseNotification(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(payload, Notification.class);
        } catch (IOException e) {
            return null;
        }
    }

    private Account getAccount(String googleId) {
        Optional<Account> accountOptional = accountRepository.findByGoogleId(googleId);
        return accountOptional.orElse(null);
    }
}
