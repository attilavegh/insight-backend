package hu.vattila.insight.notification;

import hu.vattila.insight.entity.Insight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
class EmailNotificationService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailNotificationService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    void send(Insight insight) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(new InternetAddress("elte.insight@gmail.com", "Insight"));
            helper.setTo(insight.getReceiver().getEmail());
            helper.setSubject("You've got a new Insight!");
            helper.setText(createContent(insight), true);
        } catch (Exception e) {
            return;
        }

        mailSender.send(message);
    }

    private String createContent(Insight insight) {
        Context context = new Context();

        context.setVariable("name", insight.getSender().getFullName());
        context.setVariable("profilePicture", insight.getSender().getImageUrl());
        context.setVariable("continue", insight.getContinueMessage());
        context.setVariable("consider", insight.getConsiderMessage());
        context.setVariable("hasConsider", insight.getConsiderMessage() != null);

        return templateEngine.process("mailTemplate", context);
    }
}
