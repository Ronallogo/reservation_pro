    package tg.voyage_pro.reservation_pro.core;


    
    

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import tg.voyage_pro.reservation_pro.Model.CLIENT;
import tg.voyage_pro.reservation_pro.Security.auth.AuthenticationResponse;
import tg.voyage_pro.reservation_pro.database.ClientRepository;
import tg.voyage_pro.reservation_pro.dto.ClientDTO;
import tg.voyage_pro.reservation_pro.exceptions.ClientNotFoundException;
import tg.voyage_pro.reservation_pro.mapperImpl.ClientMapperImpl;
 

import java.util.Date;
import java.util.List;
    
    


@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {
    @Autowired
    private   ClientRepository cr ;

   

    private final AuthenticationManager authenticationManager ; 



    private final ClientMapperImpl clientMapper  ; 




    
        
            
        
 

    public  AuthenticationResponse create(ClientDTO request){
        return null ; 

    
    }


    public List<ClientDTO> getAllClient(){
        return   this.clientMapper.toListDtos(this.cr.findAllOrderByIdClientDesc())   ;
        
    }

 


    public  ClientDTO getClient(Long idClient){
    
        var client = this.cr.findById(idClient).orElseThrow(()-> new ClientNotFoundException("client not found"));
        if(client == null){
            return null ;
        }
        return  this.clientMapper.toDto(client);

    }

    public  ClientDTO update(Long idClient ,     ClientDTO client){
        return null ; 


    }

    public boolean delete(Long idClient){
        if(this.cr.existsById(idClient)){
            this.cr.deleteById(idClient); ;
            return true ;
        }
        return false ;
    }


    public List<ClientDTO> searchClient(ClientDTO client){
        
        return this.clientMapper.toListDtos(this.cr.searchClient(
            client.getNomClient(),
            client.getMailClient(),
            client.getDateNaiss().toString(),
            client.getSexeClient(),
            client.getTelClient()
                
        ));
    }

    




}
