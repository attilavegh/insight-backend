package hu.vattila.insight.service.hash;

import net.jpountz.xxhash.XXHashFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashConfiguration {

    @Bean
    public XXHashFactory xxHashFactory() {
        return XXHashFactory.nativeInstance();
    }
}
