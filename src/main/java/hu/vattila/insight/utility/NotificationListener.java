package hu.vattila.insight.utility;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.sql.DataSource;
import java.sql.Statement;

public class NotificationListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private PGConnection pgConnection;

    public NotificationListener(DataSource dataSource) throws Throwable {
        if (dataSource.getConnection().isWrapperFor(PGConnection.class)) {
            pgConnection = dataSource.getConnection().unwrap(PGConnection.class);

            pgConnection.addNotificationListener(new PGNotificationListener() {
                @Override
                public void notification(int processId, String channelName, String payload) {
                    messagingTemplate.convertAndSend("/notification", payload);
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
}
