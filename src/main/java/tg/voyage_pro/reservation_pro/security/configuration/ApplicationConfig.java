package tg.voyage_pro.reservation_pro.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.database.AgentRepository;
import tg.voyage_pro.reservation_pro.database.ClientRepository;
import tg.voyage_pro.reservation_pro.exceptions.AgentNotFoundException;
import tg.voyage_pro.reservation_pro.exceptions.ClientNotFoundException;

@Configuration
@Builder
@Getter
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AgentRepository agentRepository ;
    private final ClientRepository clientRepository ;


    
  
    public UserDetailsService AgentDetailsService(){ 
        return login -> this.agentRepository.findByLogin(login)
        .orElseThrow(() -> new  AgentNotFoundException("Agent not found"));
        
    }
 
    public UserDetailsService ClientDetailsService(){
        return login -> this.clientRepository.findByLogin(login)
        .orElseThrow(() -> new  ClientNotFoundException("Client not found"));
    }

    
  
   public AuthenticationProvider ClientAuthenticationProvider(){
      DaoAuthenticationProvider authProvider    = new DaoAuthenticationProvider() ;
      authProvider.setUserDetailsService(ClientDetailsService());
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider ;
  }
 
   public AuthenticationProvider AgentAuthenticationProvider(){
      DaoAuthenticationProvider authProvider    = new DaoAuthenticationProvider() ;
      authProvider.setUserDetailsService(AgentDetailsService());
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider ;
  }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(AgentAuthenticationProvider())
            .authenticationProvider(ClientAuthenticationProvider())
            .build();
    }
    @Bean
    public LogoutHandler logoutHandler(){
        return  new SecurityContextLogoutHandler();
    }
    
    

}