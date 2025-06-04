package tg.voyage_pro.reservation_pro.Security.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

 
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity


public class SecurityConfiguration {

    private static final String[] WHITE_LIST={
        "/tg/voyage_pro/reservation/auth/**",
        "/tg/voyage_pro/reservation/auth/client/**",
        "/tg/voyage_pro/reservation/auth/agent/**",
        "/tg/voyage_pro/reservation/auth/paiement/**",
        "/tg/voyage_pro/reservation/auth/reservation/**",
        "/tg/voyage_pro/reservation/auth/voyage/**",
        "/tg/voyage_pro/reservation/auth/token/**",
        "/tg/voyage_pro/reservation/login/**",
        "/tg/voyage_pro/reservation/auth/logout/**",
        "/tg/voyage_pro/reservation/agent/register",
        "/tg/voyage_pro/reservation/agent/auth",
        "/tg/voyage_pro/reservation/client/register",
        "/tg/voyage_pro/reservation/client/auth",
        


    } ; 
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter ;
    private final AuthenticationProvider authenticationProvider ;
    private final LogoutHandler logoutHandler ;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(req ->
        req.requestMatchers(WHITE_LIST).permitAll()
        .requestMatchers("/tg/voyage_pro/reservation/auth/reservation/create").hasAnyRole("CLIENT")
        .requestMatchers("/tg/voyage_pro/reservation/auth/reservation/update").hasAnyRole("ADMIN" , "AGENT" , "CLIENT")
        .requestMatchers("/tg/voyage_pro/reservation/auth/reservation/delete").hasAnyRole("ADMIN" , "AGENT" , "CLIENT")
        .requestMatchers("/tg/voyage_pro/reservation/auth/reservation/getAll").hasAnyRole("ADMIN" , "AGENT")
        .requestMatchers("/tg/voyage_pro/reservation/auth/reservation/search").hasAnyRole("ADMIN" , "AGENT")
        .requestMatchers("/tg/voyage_pro/reservation/auth/agent/getAll").hasAnyRole("ADMIN")
        .requestMatchers("/tg/voyage_pro/reservation/auth/agent/changeRole").hasAnyRole("ADMIN")
        .requestMatchers("/tg/voyage_pro/reservation/auth/agent/create").hasAnyRole("ADMIN")
        .requestMatchers("/tg/voyage_pro/reservation/auth/agent/search").hasAnyRole("ADMIN")
        .requestMatchers("/tg/voyage_pro/reservation/auth/agent/update").hasAnyRole("ADMIN","AGENT")
        .requestMatchers("/tg/voyage_pro/reservation/auth/agent/delete").hasAnyRole("ADMIN")
        .requestMatchers("/tg/voyage_pro/reservation/auth/client/getAll").hasAnyRole("ADMIN")
         

        .anyRequest().authenticated()

    
        ) .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> 
        logout.logoutUrl("/tg/voyage_pro/reservation/auth/logout")
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler((request , response , authentification) -> SecurityContextHolder.clearContext()))
        ;
        return http.build(); 
        
        
    }
}
