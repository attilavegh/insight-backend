package hu.vattila.insight;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String url = "jdbc:pgsql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?ssl.mode=require";

        return DataSourceBuilder.create()
                .driverClassName("com.impossibl.postgres.jdbc.PGDriver")
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
}
