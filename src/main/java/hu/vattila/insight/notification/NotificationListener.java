package hu.vattila.insight.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import hu.vattila.insight.entity.Insight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Statement;

public class NotificationListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private EmailService emailService;

    private PGConnection pgConnection;

    public NotificationListener(DataSource dataSource) throws Throwable {
        if (dataSource.getConnection().isWrapperFor(PGConnection.class)) {
            pgConnection = dataSource.getConnection().unwrap(PGConnection.class);

            pgConnection.addNotificationListener(new PGNotificationListener() {
                @Override
                public void notification(int processId, String channelName, String payload) {
                    Insight insight = createParsedPayload(payload);

                    if (insight == null) {
                        return;
                    }

                    emailService.send(insight);
                    messagingTemplate.convertAndSend("/notification", insight);
                }
            });
        }
    }

    public void init() throws Throwable {
        Statement statement = pgConnection.createStatement();
        statement.execute("LISTEN insight_insert");
        statement.close();
    }

    public void destroy() throws Throwable {
        Statement statement = pgConnection.createStatement();
        statement.execute("UNLISTEN insight_insert");
        statement.close();
    }

    private Insight createParsedPayload(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(payload, Insight.class);
        } catch (IOException e) {
            return null;
        }
    }
}
