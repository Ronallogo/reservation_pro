package tg.voyage_pro.reservation_pro.security.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.security.token.TokenRepository;

@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {


    @Autowired
    private  JwtService jwtService ; 
    @Autowired
    private   CompositeUserDetailsService userDetailsService ; 
   
    @Autowired
    private  TokenRepository tokenRepository ; 
    @Override
    protected void doFilterInternal(
        @Nonnull    HttpServletRequest request,
        @Nonnull HttpServletResponse response,
        @Nonnull FilterChain filterChain) throws ServletException, IOException{

        if(request.getServletPath().contains("/tg/voyage_pro/reservation/auth" )){
            filterChain.doFilter(request, response);
            return ; 

        }

        final String authHeader  = request.getHeader("Authorization") ; 
        final String jwt ; 
        final String login ; 

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return ; 

        }

        jwt = authHeader.substring( 7) ; 
        login = this.jwtService.extractLogin(jwt) ; 

        if(login != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(login);
            var isTokenValid = this.tokenRepository.findByToken(jwt)
                .map(t-> !t.isExpired() && !t.isRevoked()) 
                .orElse(false); 
            
            if(this.jwtService.isTokenValid(jwt, userDetails) && isTokenValid ){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails , 
                    null , 
                    userDetails.getAuthorities()

                    ) ; 

                    authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    ) ; 
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            
            }
            filterChain.doFilter(request, response);
        }




         
    }
          
    
}
