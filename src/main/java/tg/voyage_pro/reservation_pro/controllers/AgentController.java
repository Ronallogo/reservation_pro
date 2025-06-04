package tg.voyage_pro.reservation_pro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tg.voyage_pro.reservation_pro.core.AgentService;
import tg.voyage_pro.reservation_pro.dto.AgentDTO;

@RestController
@RequestMapping(value = "/tg/voyage_pro/reservation/auth/agent")
@CrossOrigin("*")
 
public class AgentController {

    @Autowired
    private AgentService agentService ;


    @PutMapping("/changeRole/{email}:{role}")
    public ResponseEntity<?> changeRole(@PathVariable String email , @PathVariable String role){
        this.agentService.changeRole(email , role);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }



 

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> all(){
        var agents = this.agentService.all();
        return new ResponseEntity<>(agents , HttpStatus.OK);
    }

    @PutMapping(value = "/search")
    public ResponseEntity<?> search(@RequestBody AgentDTO agent){
        var agents = this.agentService.searchAgent(agent);
        return new ResponseEntity<>(agents , HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        var agentDeleted = this.agentService.delete(id);
        return new ResponseEntity<>(agentDeleted , HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id , @RequestBody AgentDTO agent){
        var agentUpdated = this.agentService.update(id , agent);
        return new ResponseEntity<>(agentUpdated , HttpStatus.OK);
    }
    
    
    
    
    
    
    
}
