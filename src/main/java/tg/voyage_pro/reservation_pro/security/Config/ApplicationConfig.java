package tg.voyage_pro.reservation_pro.Security.Config;


 
import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.Security.entities.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
   private final UserRepository repository ;

    /**
     * This method is used to create a UserDetailsService bean that retrieves user details from the UserRepository.
     * It throws a UsernameNotFoundException if the user is not found.
     *
     * @return UserDetailsService
     */

   @Bean
    public UserDetailsService userDetailsService(){
    return   username -> repository.findByUsername(username)
            .orElseThrow(()-> new  UsernameNotFoundException("User not found"));
   }

   @Bean
    public AuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider authProvider    = new DaoAuthenticationProvider() ;
       authProvider.setUserDetailsService(userDetailsService());
       authProvider.setPasswordEncoder(passwordEncoder());
       return authProvider ;
   }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder( );
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws  Exception{
        return  config.getAuthenticationManager() ;
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new SecurityContextLogoutHandler();
    }


}
