package tg.voyage_pro.reservation_pro.core;



 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tg.voyage_pro.reservation_pro.Model.AGENT;
import tg.voyage_pro.reservation_pro.database.AgentRepository;
import tg.voyage_pro.reservation_pro.dto.AgentDTO;
import tg.voyage_pro.reservation_pro.exceptions.AgentNotFoundException;
import tg.voyage_pro.reservation_pro.mapperImpl.AgentMapperImpl;
import tg.voyage_pro.reservation_pro.security.user.Roles;

import java.util.List;
import java.util.Objects;


@Service
public class AgentService {

    @Autowired
    private AgentRepository repo ;
    private AgentMapperImpl mapper = new AgentMapperImpl();


    public  AGENT create(  AgentDTO agent){
        System.out.print(agent.toString());
        return   this.repo.save(this.mapper.toEntity(agent));
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
}
