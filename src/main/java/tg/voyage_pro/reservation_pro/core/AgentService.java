package tg.voyage_pro.reservation_pro.core;



 
 
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 
import org.springframework.stereotype.Service;
 
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.Model.AGENT;
import tg.voyage_pro.reservation_pro.Security.auth.AuthenticationRequest;
import tg.voyage_pro.reservation_pro.Security.auth.AuthenticationResponse;
import tg.voyage_pro.reservation_pro.Security.auth.AuthenticationService;
import tg.voyage_pro.reservation_pro.Security.auth.RegisterRequest;
import tg.voyage_pro.reservation_pro.Security.entities.Roles;
import tg.voyage_pro.reservation_pro.database.AgentRepository;
import tg.voyage_pro.reservation_pro.dto.AgentDTO;
import tg.voyage_pro.reservation_pro.exceptions.AgentNotFoundException;
import tg.voyage_pro.reservation_pro.mapperImpl.AgentMapperImpl;
 

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AgentService {

      
 

    private final  AuthenticationService auth ; 

 
   


   
    private final AgentRepository repo ;
   
    private  final AgentMapperImpl mapper   ; 
 

    public   AuthenticationResponse create(  AgentDTO a){
        var response = this.auth.register(RegisterRequest.builder()
            .password(a.getPassword())
            .role(Roles.AGENT)
            .username(a.getLogin())
            .build()
        ) ; 
        var agent = this.mapper.toEntityForRegistration(a) ; 

        agent.setUser(response.getUser());
        
        this.repo.save(agent);
        return AuthenticationResponse.builder()
            .accessToken(response.getAccessToken())
            .refreshToken(response.getRefreshToken())
            .build() ; 
      

      

       





 
    }

    public AuthenticationResponse authentication(AuthenticationRequest request){
        return this.auth.authenticate(request);
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
       
        Long idAgent = a.getIdAgent() ; 
       
        a =  this.mapper.toEntity(agent) ; 
       
        a.setIdAgent(idAgent);
        System.out.println(a.toString());
        
        return  this.mapper.toDto(this.repo.save(a)) ;

    }

  

    public void changeRole(String email , String role){
        var agent = this.repo.findByMailAgent(email).orElseThrow(()-> new AgentNotFoundException("Aucun agent n'a ce numéro"));
       // agent.setRole(setRoleUser(role));
        this.repo.save(agent);

    }
    public Roles setRoleUser(String role){
       // if(Objects.equals(role, Roles.AGENT.name())) return Roles.AGENT ;
       // if(Objects.equals(role, Roles.ADMIN.name())) return Roles.ADMIN ;

        //else throw new RuntimeException("Error Role") ;
        return null ; 
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

    

 

    
}
