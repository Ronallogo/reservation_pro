package tg.voyage_pro.reservation_pro.controllers;

import java.net.http.HttpClient;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
 
import tg.voyage_pro.reservation_pro.Model.RESERVATION;
import tg.voyage_pro.reservation_pro.core.ReservationService;
import tg.voyage_pro.reservation_pro.dto.ReservationDTO;

@RestController
@RequestMapping(path = "/tg/voyage_pro/reservation/auth/reservation")
@CrossOrigin("*")
public class ReservationController {

    @Autowired
    private ReservationService service ; 


    @PostMapping(path="/create" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> create(@RequestBody ReservationDTO reservation){
        var response =  this.service.create(reservation) ; 

        return new ResponseEntity<>(response , HttpStatus.OK);
    }


    @GetMapping(path = "/all")
    public List<ReservationDTO> all(){
        return this.service.getAll() ;
    }
    @GetMapping(path = "/all/{email}")
    public List<ReservationDTO> allForOne(@PathVariable String email){
        return this.service.getAllForOne(email) ;
    }

    @GetMapping(path = "/update")
    public ReservationDTO update(@RequestBody ReservationDTO reservation){
        return this.service.update(reservation) ; 
    }

    @DeleteMapping(path = "/delete/{id}")
    public boolean delete(@PathVariable Long id){
        return this.service.delete(id);
    }

    @PutMapping(path="/confirmation/{id}")
    public ResponseEntity<?> confirmation(@PathVariable Long id){
        var response = this.service.confirmer(id);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PutMapping(path="/annulation/{id}")
    public ResponseEntity<?> annulation(@PathVariable Long id){
        var response = this.service.annulee(id);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PutMapping(path="/payment/{id}")
    public ResponseEntity<?> payment(@PathVariable Long id){
        var response = this.service.payee(id);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PutMapping(value="/researchForOne")
    public List<ReservationDTO> researchForOne(@RequestBody ReservationDTO res){
        return this.service.research(res) ; 
    }
    

}
