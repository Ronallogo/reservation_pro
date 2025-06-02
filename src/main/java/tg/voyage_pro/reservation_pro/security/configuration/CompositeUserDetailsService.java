package tg.voyage_pro.reservation_pro.security.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
 

@Component
@RequiredArgsConstructor
public class CompositeUserDetailsService implements UserDetailsService{
  

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.contains("@agent.")) {
            return ApplicationConfig.builder()
            .build()
            .AgentDetailsService()
            .loadUserByUsername(username);
        } else {
            return ApplicationConfig.builder()
            .build()
            .AgentDetailsService()
            .loadUserByUsername(username);
        }
    }

}
