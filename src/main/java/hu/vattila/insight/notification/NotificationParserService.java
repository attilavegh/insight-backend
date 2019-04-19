package hu.vattila.insight.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.vattila.insight.entity.Account;
import hu.vattila.insight.entity.Insight;
import hu.vattila.insight.dto.NotificationDto;
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
        NotificationDto notificationDto = parseNotification(payload);
        if (notificationDto == null) {
            return null;
        }

        Insight insight = new Insight();
        insight.setId(notificationDto.getId());
        insight.setContinueMessage(notificationDto.getContinueMessage());
        insight.setConsiderMessage(notificationDto.getConsiderMessage());
        insight.setDate(notificationDto.getDate());

        Account sender = getAccount(notificationDto.getSender());
        Account receiver = getAccount(notificationDto.getReceiver());

        if (sender == null || receiver == null) {
            return null;
        }

        insight.setSender(sender);
        insight.setReceiver(receiver);

        return insight;
    }

    private NotificationDto parseNotification(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(payload, NotificationDto.class);
        } catch (IOException e) {
            return null;
        }
    }

    private Account getAccount(String googleId) {
        Optional<Account> accountOptional = accountRepository.findByGoogleId(googleId);
        return accountOptional.orElse(null);
    }
}
