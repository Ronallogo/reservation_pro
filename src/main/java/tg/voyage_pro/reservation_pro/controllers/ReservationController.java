package tg.voyage_pro.reservation_pro.controllers;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    @GetMapping(path="/confirmation/{emailAgent}/{id}")
    public ResponseEntity<?> confirmation(@PathVariable Long id ,   @PathVariable String emailAgent){
        var response = this.service.confirmer(id , emailAgent);
        Map<String , Object> o = new HashMap<>() ; 
        o.put("value" , response) ; 
        return new ResponseEntity<>( o , HttpStatus.OK);
    }

    @GetMapping(path="/annulation/{id}")
    public ResponseEntity<?> annulation(@PathVariable Long id){
        this.service.annulee(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping(path="/payment/{id}")
    public ResponseEntity<?> payment(@PathVariable Long id){
        var response = this.service.payee(id);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PutMapping(value="/researchForOne")
    public List<ReservationDTO> researchForOne(@RequestBody ReservationDTO res){
        return this.service.researchForOne(res) ; 
    }
    @PutMapping(value="/researchForOneAnnulee")
    public List<ReservationDTO> researchForOneAnnulee(@RequestBody ReservationDTO res){
        return this.service.researchForOneAnnulee(res) ; 
    }
    @PutMapping(value="/researchForOneEncours")
    public List<ReservationDTO> researchForOneEncours(@RequestBody ReservationDTO res){
        return this.service.researchForOneEncours(res) ; 
    }
    @PutMapping(value="/research")
    public List<ReservationDTO> research(@RequestBody ReservationDTO res){
        return this.service.researchForOne(res) ; 
    }
    @PutMapping(value="/researchAnnulee")
    public List<ReservationDTO> researchAnnulee(@RequestBody ReservationDTO res){
        return this.service.researchAnnulee(res) ; 
    }
    @PutMapping(value="/researchEnCours")
    public List<ReservationDTO> researchEnCours(@RequestBody ReservationDTO res){
        return this.service.researchEncour(res) ; 
    }

    @GetMapping(value="/reservationRecentePourcentage")
    public  ResponseEntity<?> reservationsRecenteFloat(){
        var response = this.service.reservationsRecent() ; 
        Map<String , Object> o = new HashMap<>() ; 
        o.put( "reservation_average", response) ; 

        return new ResponseEntity<>(o , HttpStatus.OK) ; 

    }

    @GetMapping(value="/reservationParClient" )
    public ResponseEntity<?> reservationParClient(){
        var response = this.service.reservationParclient() ; 
        Map<String  ,Object> o = new HashMap<>()  ; 
        o.put("value" , response) ; 

        return new  ResponseEntity<>(o , HttpStatus.OK) ; 
    }


    @GetMapping(value="/reservationEnCoursForOne/{email}")
    public List<ReservationDTO> reservationEnCoursForOne(@PathVariable String email){
        return this.service.getReservationsEnCoursForOne(email) ; 

    }
    @GetMapping(value="/reservationEnCours")
    public List<ReservationDTO> reservationEnCours(){
        return this.service.getReservationsEnCours() ; 

    }

    @GetMapping(value="/reservationAnnulee")
    public List<ReservationDTO> reservationAnnuler(){
        return this.service.getReservationsAnnulee() ; 
    }
    @GetMapping(value="/reservationAnnuleeForOne/{email}")
    public List<ReservationDTO> reservationAnnuler(@PathVariable String email){
        return this.service.getReservationsAnnuleeForOne(email) ; 
    }


    @GetMapping(value = "/dataByMonth/{mois}")
    public ResponseEntity<?> getData(@PathVariable Integer mois){
        return new ResponseEntity<>(this.service.data(mois) , HttpStatus.OK);
    }

     @GetMapping(path="/newEarnPourcentage")
    public ResponseEntity<?> newEarnPourcentage(){
        Map<String ,  Object> o = this.service.newEarnPourcentage() ; 

        return new ResponseEntity<>(o , HttpStatus.OK);
    }


    @GetMapping(value = "/gainTotal")
    public ResponseEntity<?> gainTotal(){
        return new ResponseEntity<>(this.service.TotalEarn() , HttpStatus.OK) ; 
    }

    @GetMapping(value="/revenuMoyen")
    public ResponseEntity<?> revenuMoyen(){
        return new ResponseEntity<>(this.service.revenuMoyen() , HttpStatus.OK) ; 

    }

    @GetMapping(value = "/tauxAnnulation")
     public ResponseEntity<?>  tauxAnnulation(){
        return new ResponseEntity<>(this.service.tauxAnnulation() , HttpStatus.OK) ; 

    }

 
    

}
