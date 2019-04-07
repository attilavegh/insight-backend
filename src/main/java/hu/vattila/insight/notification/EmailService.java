package hu.vattila.insight.notification;

import hu.vattila.insight.entity.Account;
import hu.vattila.insight.entity.Insight;
import hu.vattila.insight.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AccountRepository accountRepository;


    void send(Insight insight) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        Account receiver = getReceiver("115214942166386882750");

        try {
            helper.setFrom(new InternetAddress("elte.insight@gmail.com", "Insight"));
            helper.setTo(receiver.getEmail());
            helper.setSubject("You've got a new insight!");
            helper.setText(insight.getContent());
        } catch (Exception e) {
            return;
        }

        javaMailSender.send(message);
    }

    private Account getReceiver(String googleId) {
        Optional<Account> accountOptional = accountRepository.findByGoogleId(googleId);
        return accountOptional.orElse(null);
    }
}
