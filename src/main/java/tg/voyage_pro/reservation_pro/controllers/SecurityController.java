package tg.voyage_pro.reservation_pro.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.Security.auth.AuthenticationRequest;
import tg.voyage_pro.reservation_pro.core.AgentService;
import tg.voyage_pro.reservation_pro.core.ClientService;
import tg.voyage_pro.reservation_pro.dto.AgentDTO;
import tg.voyage_pro.reservation_pro.dto.ClientDTO;
 

import org.springframework.web.bind.annotation.PostMapping;
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
   
        return  new ResponseEntity<>(  null ,  HttpStatusCode.valueOf(200));
    }

    @PostMapping("agent/register")
    public  ResponseEntity<?>  createAgent(@RequestBody AgentDTO entity) {

       // var response = this.agentService.create(entity);
        return new ResponseEntity<>( null , HttpStatus.CREATED) ; 
    }
    @PostMapping("client/register")
    public  ResponseEntity<?>  createClient(@RequestBody ClientDTO entity) {

        var response = this.clientService.create(entity);
        return new ResponseEntity<>(response , HttpStatus.CREATED) ; 
    }
    

    @PostMapping("client/auth")
    public  ResponseEntity<?>  authentificationClient(@RequestBody AuthenticationRequest  request) {
      //  var response = this.clientService.authenticate(request);
        return  new ResponseEntity<>(null ,  HttpStatusCode.valueOf(200));
    }

     
    

}
