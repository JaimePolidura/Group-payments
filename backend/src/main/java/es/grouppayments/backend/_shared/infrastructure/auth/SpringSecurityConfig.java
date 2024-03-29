package es.grouppayments.backend._shared.infrastructure.auth;

import es.grouppayments.backend.users.users._shared.domain.UserState;
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
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers(this.routesWithoutAuthentication())
                    .permitAll()

                .antMatchers(this.routesWithSignUpAllCompleted())
                    .hasAuthority(UserState.SIGNUP_ALL_COMPLETED.name())

                .antMatchers(this.routesWithSignUpOauthCompleted())
                    .hasAuthority(UserState.SIGNUP_OAUTH_COMPLETED.name())

                .antMatchers(this.routesWithSignOauthCreditCardCompleted())
                    .hasAuthority(UserState.SIGNUP_OAUTH_CREDIT_CARD_COMPLETED.name())

                .anyRequest()
                    .authenticated();

        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);
    }

    private String[] routesWithSignOauthCreditCardCompleted(){
        return new String[]{
                "/payments/stripe/getconnectedaccountlink"
        };
    }

    private String[] routesWithSignUpAllCompleted(){
        return new String[]{
                "/usersimage/change/**",
                "/groups/**",
                "/payments/paymentshistory",
                "/payments/transfer",
                "/notifications/offline/**",
                "/users/**",
                "/invitations/**",
                "/payments/stripe/register",
        };
    }

    private String[] routesWithoutAuthentication(){
        return new String[]{
                "/payments/stripe/setupintent",
                "/auth/oauth/**",
                "/notifications/online/**",
                "/users/delete/confirm",
                "/users/supportedcurrencies/**",
                "/usersimage/get/**",
                "/payments/stripe/changecard/confirm"
        };
    }

    private String[] routesWithSignUpOauthCompleted(){
        return new String[]{
                "/payments/stripe/register",
        };
    }
}
