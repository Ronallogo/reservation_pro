package tg.voyage_pro.reservation_pro.core;



 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.Model.AGENT;
import tg.voyage_pro.reservation_pro.database.AgentRepository;
import tg.voyage_pro.reservation_pro.dto.AgentDTO;
import tg.voyage_pro.reservation_pro.exceptions.AgentNotFoundException;
import tg.voyage_pro.reservation_pro.mapperImpl.AgentMapperImpl;
import tg.voyage_pro.reservation_pro.security.auth.AuthentificationRequest;
import tg.voyage_pro.reservation_pro.security.auth.AuthentificationResponse;
import tg.voyage_pro.reservation_pro.security.configuration.JwtService;
import tg.voyage_pro.reservation_pro.security.token.TOKEN;
import tg.voyage_pro.reservation_pro.security.token.TokenRepository;
import tg.voyage_pro.reservation_pro.security.user.Roles;
import tg.voyage_pro.reservation_pro.security.user.TypeToken;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AgentService {

      
    private final JwtService jwtService ; 

    private final AuthenticationManager authenticationManager ; 

    private final TokenRepository tokenRepository ; 

   


   
    private final AgentRepository repo ;
   
    private  final AgentMapperImpl mapper   ; 



    public void saveAgentToken(AGENT agent , String jwtToken){
        var token = TOKEN.builder()
            .token(jwtToken)
            .tokenType(TypeToken.BEARER)
            .expired(false)
            .expiration( new Date(System.currentTimeMillis() +   30 * 60 * 1000))
            .revoked(false)
            .agent(agent)
            .role(Roles.AGENT)
            .build() ; 
        tokenRepository.save(token);
    }

    public   AuthentificationResponse create(  AgentDTO a){
         
        var agent = this.mapper.toEntityForRegistration(a) ; 
        var saveAgent = this.repo.save(agent);
        var jwtToken = jwtService.generateToken(agent) ; 
        var refresh_token = jwtService.generateRefreshToken(agent) ; 

        saveAgentToken(saveAgent, jwtToken);

        return AuthentificationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refresh_token)
            .build() ; 






 
    }

    public List<AgentDTO>   all(){
        return this.mapper.toListDto(this.repo.findAllOrderByIdAgentDesc())  ;
    }

    public List<AgentDTO> searchAgent(AgentDTO agent){
        return this.mapper.toListDto(this.repo.searchAgent(
            agent.getNomAgent(), agent.getMailAgent(),
            agent.getDateNaiss().toString(),
            agent.getSexeAgent(),
             agent.getTelAgent()));
    }


    public  AgentDTO update(Long id ,  AgentDTO agent){
        if(id == null){
            throw new NullPointerException("idAgent est null");
        }
        var a = this.repo.findById(id).orElseThrow(()-> new AgentNotFoundException("Aucun agent n'a ce numéro"));
        String login = a.getLogin();
        Long idAgent = a.getIdAgent() ; 
        String password = a.getPassword();
        a =  this.mapper.toEntity(agent) ; 
        a.setLogin(login);
        a.setPassword(password);
        a.setIdAgent(idAgent);
        System.out.println(a.toString());
        
        return  this.mapper.toDto(this.repo.save(a)) ;

    }

    public AuthentificationResponse authenticate(AuthentificationRequest request){

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getLogin(),  request.getPassword())
        ) ; 

        var agent = this.repo.findByLogin(request.getLogin())
            .orElseThrow(()-> new AgentNotFoundException( "agent not found")) ; 


        var jwtToken = jwtService.generateRefreshToken(agent) ;
        var  refresh_token =  jwtService.generateRefreshToken(agent) ;
        revokeAllTokenaAgent(agent);
        saveAgentToken(agent, jwtToken);

        return AuthentificationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refresh_token)
            .build() ;

        

        
 

    }

    public void changeRole(String email , String role){
        var agent = this.repo.findByMailAgent(email).orElseThrow(()-> new AgentNotFoundException("Aucun agent n'a ce numéro"));
        agent.setRole(setRoleUser(role));
        this.repo.save(agent);

    }
    public Roles setRoleUser(String role){
        if(Objects.equals(role, Roles.AGENT.name())) return Roles.AGENT ;
        if(Objects.equals(role, Roles.ADMIN.name())) return Roles.ADMIN ;

        else throw new RuntimeException("Error Role") ;
    }



    public boolean delete(Long id){
        if(this.repo.existsById(id)){
                this.repo.deleteById(id) ;
                return true ;
        }
        return  false ;
    }
    public AGENT get(Long id){
        return this.repo.findById(id).orElse(null);
    }

    public void revokeAllTokenaAgent(AGENT agent){
        var validAgentToken = this.tokenRepository.findAllValidTokenByAgent(agent.getIdAgent()) ; 
        if(validAgentToken.isEmpty()){
            return ; 
        }
        validAgentToken.forEach(token->{
            token.setExpired(true) ;
            token.setRevoked(true);
        });
        this.tokenRepository.saveAll(validAgentToken);
    }



    public void refreshToken(
        HttpServletRequest request , 
        HttpServletResponse response
    ) throws IOException{

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION) ; 
        final String refresh_token ; 
        final String login ; 

        if(authHeader ==null || !authHeader.startsWith("Bearer ")){
            return ; 
        }

        refresh_token = authHeader.substring(7);
        login = jwtService.extractLogin(refresh_token) ; 

        if(login != null){
            var agent = this.repo.findByLogin(login)
            .orElseThrow(()-> new AgentNotFoundException("Agent not found"));

            if(jwtService.isTokenValid(refresh_token, agent)){
                var accessToken = jwtService.generateRefreshToken(agent);
                revokeAllTokenaAgent(agent);
                saveAgentToken( agent,  accessToken);
                var authResponse =  AuthentificationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refresh_token)
                .build() ; 

                new ObjectMapper().writeValue(response.getOutputStream(),  authResponse);
            }
        }


    }
}
