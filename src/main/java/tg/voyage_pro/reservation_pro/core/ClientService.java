    package tg.voyage_pro.reservation_pro.core;


    
    

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.Security.auth.AuthenticationRequest;
import tg.voyage_pro.reservation_pro.Security.auth.AuthenticationResponse;
import tg.voyage_pro.reservation_pro.Security.auth.AuthenticationService;
import tg.voyage_pro.reservation_pro.Security.auth.RegisterRequest;
import tg.voyage_pro.reservation_pro.Security.entities.Roles;
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

   

    private final AuthenticationService auth ; 



    private final ClientMapperImpl clientMapper  ; 




    
    public AuthenticationResponse authentication(AuthenticationRequest request){
        return this.auth.authenticate(request);
    }
            
        
 

    public  AuthenticationResponse create(ClientDTO  a){
        var response = this.auth.register( RegisterRequest.builder()
            .password(a.getPassword())
            .role(Roles.CLIENT)
            .username(a.getLogin())
            .build()
        ) ; 
        
    
    var  client = this.clientMapper.toEntityForRegistration(a) ; 

     client.setUser(response.getUser());
    
    this.cr.save(client);
    return AuthenticationResponse.builder()
        .accessToken(response.getAccessToken())
        .refreshToken(response.getRefreshToken())
        .build() ; 
  


    
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
