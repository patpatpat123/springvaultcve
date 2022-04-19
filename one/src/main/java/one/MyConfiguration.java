package one;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        final var httpSecurity = http.authorizeRequests().antMatchers("/health").permitAll().and().httpBasic().and().csrf().ignoringAntMatchers("/health").and().authorizeRequests();
        httpSecurity.anyRequest().authenticated().and().x509().subjectPrincipalRegex("UID=(.*?)(?:,|$)").userDetailsService(username -> new User(username, "password", AuthorityUtils.commaSeparatedStringToAuthorityList(username))).and().saml2Login().relyingPartyRegistrationRepository(new InMemoryRelyingPartyRegistrationRepository());
    }

}
