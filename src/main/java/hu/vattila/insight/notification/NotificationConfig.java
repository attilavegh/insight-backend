package hu.vattila.insight.notification;

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
