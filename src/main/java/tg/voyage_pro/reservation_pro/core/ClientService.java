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
import tg.voyage_pro.reservation_pro.dto.UserDTO_2;
import tg.voyage_pro.reservation_pro.exceptions.ClientNotFoundException;
import tg.voyage_pro.reservation_pro.mapperImpl.ClientMapperImpl;
 

import java.util.Date;
import java.util.List;
    
    


@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {
    @Autowired
    private   ClientRepository repo ;

   

    private final AuthenticationService auth ; 



    private final ClientMapperImpl clientMapper  ; 




    
    public AuthenticationResponse authentication(AuthenticationRequest request){
        return this.auth.authenticate(request);
    }

    public  AuthenticationResponse updateUser(UserDTO_2 data , String email){
        var client = this.repo.findByMailClient(email)
        .orElseThrow(()-> new ClientNotFoundException( "client not found")) ; 
        client.setMailClient(data.getEmail());
        client.setTelClient(data.getPhone());
        client.setNomClient(data.getLastname());
        client.setPrenomClient(data.getFirstname());

        this.repo.save(client) ; 

        return  this.auth.update( RegisterRequest.builder()
            .password(client.getUser().getPassword())
            .role(Roles.CLIENT)
            .username(client.getUser().getUsername())
            .build()
        );   

    }
            
        
 

    public  AuthenticationResponse  register(ClientDTO  a){
        var  client = this.clientMapper.toEntityForRegistration(a) ; 


        var response = this.auth.register( RegisterRequest.builder()
            .password(a.getPassword())
            .role(Roles.CLIENT)
            .username(a.getLogin())
            .build() , client
            
        ) ; 
        

    
    return AuthenticationResponse.builder()
        .accessToken(response.getAccessToken())
        .refreshToken(response.getRefreshToken())
        .build() ; 
  


    
    }


    public List<ClientDTO> getAllClient(){
        return   this.clientMapper.toListDtos(this.repo.findAllOrderByIdClientDesc())   ;
        
    }

 


    public  ClientDTO getClient(Long idClient){
    
        var client = this.repo.findById(idClient).orElseThrow(()-> new ClientNotFoundException("client not found"));
        if(client == null){
            return null ;
        }
        return  this.clientMapper.toDto(client);

    }

    public  ClientDTO update(Long idClient ,     ClientDTO client){
        return null ; 


    }

    public boolean delete(Long idClient){
        if(this.repo.existsById(idClient)){
            this.repo.deleteById(idClient); ;
            return true ;
        }
        return false ;
    }


    public List<ClientDTO> searchClient(ClientDTO client){
        
        return this.clientMapper.toListDtos(this.repo.searchClient(
            client.getNomClient(),
            client.getMailClient(),
            client.getDateNaiss().toString(),
            client.getSexeClient(),
            client.getTelClient()
                
        ));
    }

    




}
