package tg.voyage_pro.reservation_pro.core;


 
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
 
import tg.voyage_pro.reservation_pro.Model.CLIENT;
import tg.voyage_pro.reservation_pro.database.ClientRepository;
import tg.voyage_pro.reservation_pro.dto.ClientDTO;
import tg.voyage_pro.reservation_pro.exceptions.ClientNotFoundException;
 

 
import java.util.List;
import java.util.stream.Collectors;
 


@Service
@Transactional
 
public class ClientService {
    @Autowired
    private   ClientRepository cr ;
    
 
 
   

    public CLIENT create(ClientDTO client){
        CLIENT c =  this.cr.save(
            CLIENT.builder()
            .nomClient(client.getNomClient())
            .prenomClient(client.getPrenomClient())
            .mailClient( client.getMailClient())
            .sexeClient( client.getSexeClient())
            .dateNaiss( client.getDateNaiss())
            .login( client.getLogin())
            .telClient( client.getTelClient())
            .password( client.getPassword())
            .build());
        
        c.setLogin("hided");
        c.setPassword( "hided");
        return c ; 
       
    }


    public List<ClientDTO> getAllClient(){
        return this.cr.findAll().stream().map(x->
        ClientDTO.builder()
        .nomClient(x.getNomClient())
        .prenomClient(x.getPrenomClient())
        .mailClient( x.getMailClient())
        .sexeClient(x.getSexeClient())
        .dateNaiss( x.getDateNaiss())
        .telClient( x.getTelClient()) 
        .build()
        
        ).collect(Collectors.toList());
        
    }


    public ClientDTO getClient(Long idClient){
       
        if(!this.cr.existsById(idClient)){
            throw new ClientNotFoundException("Aucun client n'a ce numéro");
        }

        var client =  this.cr.findById(idClient).map( x-> 
        
            ClientDTO.builder()
            .idClient(x.getIdClient())
            .nomClient(x.getNomClient())
            .prenomClient(x.getPrenomClient())
            .mailClient( x.getMailClient())
            .sexeClient(x.getSexeClient())
            .dateNaiss( x.getDateNaiss())
            .telClient( x.getTelClient()) 
            .build()
        );

        return client.get() ; 
        

    }

    public ClientDTO update(ClientDTO client){
       if(!this.cr.existsById(client.getIdClient())){
            throw new ClientNotFoundException("Aucun client n'a ce numéro");
        }

        CLIENT c = this.cr.findById(client.getIdClient()).get();

        client.setLogin(c.getLogin());
        client.setPassword(c.getPassword());

        BeanUtils.copyProperties(client, c);
        this.cr.save(c);
        client.setLogin("");
        client.setPassword( "");

        return client ; 




    }

    public boolean delete(Long idClient){
        if(this.cr.existsById(idClient)){
            this.cr.deleteById(idClient); ;
            return true ;
        }
        return false ;
    }






}
