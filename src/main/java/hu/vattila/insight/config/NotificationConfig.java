package hu.vattila.insight.config;

import hu.vattila.insight.notification.NotificationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class NotificationConfig {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public NotificationListener notificationListener(DataSource dataSource) throws Throwable {
        return new NotificationListener(dataSource);
    }
}
