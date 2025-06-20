package tg.voyage_pro.reservation_pro.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.Security.auth.AuthenticationRequest;
 
import tg.voyage_pro.reservation_pro.core.AgentService;
import tg.voyage_pro.reservation_pro.core.ClientService;
import tg.voyage_pro.reservation_pro.dto.AgentDTO;
import tg.voyage_pro.reservation_pro.dto.ClientDTO;
import tg.voyage_pro.reservation_pro.dto.UserDTO_2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/tg/voyage_pro/reservation/")

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class SecurityController {

    private final AgentService agentService ; 
    private final ClientService clientService ; 
     
    @PostMapping("agent/auth")
    public  ResponseEntity<?>  authentificationAgent(@RequestBody AuthenticationRequest  request) {
        var response = this.agentService.authentication(request);
        return  new ResponseEntity<>(response  ,  HttpStatusCode.valueOf(200));
    }

    @PutMapping("user/update/{role}/{email}")
    public  ResponseEntity<?> userUpdate( @PathVariable String email ,  @PathVariable String role , @RequestBody UserDTO_2 data){
        System.out.println(role.equals("CLIENT"));
        if(role.equals("CLIENT")){
            var response =  this.clientService.updateUser( data, email) ; 
            return new ResponseEntity<>(response , HttpStatus.OK) ; 
        }else{
            var response = this.agentService.updateUser(data , email);
            return new ResponseEntity<>(response , HttpStatus.OK) ; 
        }
        
    }

    @PostMapping("agent/register")
    public  ResponseEntity<?>  createAgent(@RequestBody AgentDTO entity) {

       var response = this.agentService.create(entity);
        return new ResponseEntity<>( response , HttpStatus.CREATED) ; 
    }
    @PostMapping("client/register")
    public  ResponseEntity<?>  createClient(@RequestBody ClientDTO entity) {

        var response = this.clientService.register(entity);
        return new ResponseEntity<>(response , HttpStatus.CREATED) ; 
    }
    

    @PostMapping("client/auth")
    public  ResponseEntity<?>  authentificationClient(@RequestBody AuthenticationRequest  request) {
        var response = this.clientService.authentication(request);
        return  new ResponseEntity<>( response ,  HttpStatusCode.valueOf(200));
    }

     
    

}
