package tg.voyage_pro.reservation_pro.security.configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class CompositeAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO Auto-generated method stub
        String username = authentication.getName();
        
        if (username.startsWith("@agent.")) {
            return    ApplicationConfig.builder().build().AgentAuthenticationProvider().authenticate(authentication);
        } else {
            return  ApplicationConfig.builder().build().ClientAuthenticationProvider().authenticate(authentication);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
