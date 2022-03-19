package es.grouppayments.backend._shared.infrastructure.auth;

import es.grouppayments.backend.users._shared.domain.UserState;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JWTFilterRequest jwtFilterRequest;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/auth/oauth/**", "/sse", "/socket/**").permitAll()
                .antMatchers("/groups/**")
                    .hasAuthority(UserState.SIGNUP_ALL_COMPLETED.name())
                .antMatchers("/payments/stripe/setupintent", "/payments/stripe/createcustomer")
                    .hasAuthority(UserState.SIGNUP_OAUTH_COMPLETED.name())
                .anyRequest().authenticated();

        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);
    }
}
