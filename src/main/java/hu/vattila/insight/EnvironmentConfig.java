package hu.vattila.insight;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Profile("prod")
class ProductionDatabaseConfig {

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String driverClass = "com.impossibl.postgres.jdbc.PGDriver";
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String url = "jdbc:pgsql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        return DataSourceBuilder.create()
                .driverClassName(driverClass)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
}

@Configuration
@Profile("dev")
class DevDatabaseConfig {

    @Bean
    public DataSource dataSource() {

        String driverClass = "com.impossibl.postgres.jdbc.PGDriver";
        String url = "jdbc:pgsql://localhost:5432/postgres";
        String username = "postgres";
        String password = "1234";

        return DataSourceBuilder.create()
                .driverClassName(driverClass)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
}
