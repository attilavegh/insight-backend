package hu.vattila.insight.authentication;

import hu.vattila.insight.dto.authentication.AuthConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        AuthConstants.STATIC_IMG.getValue(),
                        AuthConstants.WS_ENDPOINT.getValue(),
                        AuthConstants.LOGIN_ENDPOINT.getValue(),
                        AuthConstants.REFRESH_ENDPOINT.getValue()).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new TokenFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}

