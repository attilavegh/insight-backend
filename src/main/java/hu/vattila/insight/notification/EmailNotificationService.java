package hu.vattila.insight.notification;

import hu.vattila.insight.entity.Insight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
class EmailNotificationService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    void send(Insight insight) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(new InternetAddress("elte.insight@gmail.com", "Insight"));
            helper.setTo(insight.getReceiver().getEmail());
            helper.setSubject("You've got a new Insight!");
            helper.setText(insight.getContinueMessage() + " <br> " + insight.getConsiderMessage(), true);
        } catch (Exception e) {
            return;
        }

        mailSender.send(message);
    }
}
