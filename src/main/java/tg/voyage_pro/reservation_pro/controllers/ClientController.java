package tg.voyage_pro.reservation_pro.controllers;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tg.voyage_pro.reservation_pro.Model.CLIENT;
import tg.voyage_pro.reservation_pro.core.ClientService;
import tg.voyage_pro.reservation_pro.dto.ClientDTO;

import java.util.List;

@RestController
@RequestMapping(value = "/tg/voyage_pro/reservation/auth/client")
@CrossOrigin("*")

public class ClientController {
    @Autowired
    private ClientService clientService ;


    

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll(){
        List<ClientDTO> listClient = this.clientService.getAllClient() ;
        return new ResponseEntity<>(listClient , HttpStatus.OK);
    }

    @GetMapping(value = "/get/{idClient}")
    public ResponseEntity<?> get(@PathVariable Long idClient){
         ClientDTO client = this.clientService.getClient(idClient);
        return new ResponseEntity<>(client , HttpStatus.FOUND);
    }

    @PutMapping(value = "/update/{idClient}")
    public ResponseEntity<?> update(@PathVariable Long idClient ,     @RequestBody  ClientDTO clientUpdated){
         ClientDTO client = this.clientService.update(idClient ,   clientUpdated);
        return new ResponseEntity<>(client , HttpStatus.OK) ;
    }

    @DeleteMapping(value="/delete/{idClient}")
    public ResponseEntity<?> delete(@PathVariable Long idClient){
        var response = this.clientService.delete(idClient);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PutMapping(value = "/search")
    public ResponseEntity<?> search(@RequestBody ClientDTO client){
        List<ClientDTO> clients = this.clientService.searchClient(client);
        return new ResponseEntity<>(clients , HttpStatus.OK);
    }

    




}
