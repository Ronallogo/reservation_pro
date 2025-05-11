package tg.voyage_pro.reservation_pro.client;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tg/voyage_pro/reservation/auth/client")
@CrossOrigin("*")

public class ClientController {
    @Autowired
    private ClientService clientService ;


    @PostMapping(value = "/create"  ,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create( @RequestBody ClientDTO c){
            ClientDTO client = this.clientService.create(c);
            return new ResponseEntity<>(client , HttpStatusCode.valueOf(200)) ;

    }

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

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody ClientDTO clientUpdated){
        ClientDTO client = this.clientService.update(clientUpdated);
        return new ResponseEntity<>(client , HttpStatus.OK) ;
    }

    @DeleteMapping(value="/delete/{idClient}")
    public ResponseEntity<?> delete(@PathVariable Long idClient){
        var response = this.clientService.delete(idClient);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }




}
