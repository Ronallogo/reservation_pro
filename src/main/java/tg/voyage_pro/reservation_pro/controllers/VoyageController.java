package tg.voyage_pro.reservation_pro.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import tg.voyage_pro.reservation_pro.Model.VOYAGE;
import tg.voyage_pro.reservation_pro.core.VoyageService;
import tg.voyage_pro.reservation_pro.dto.VoyageDTO;
 





@RestController
@RequestMapping(value = "/tg/voyage_pro/reservation/auth/voyage")
@CrossOrigin("*")
public class VoyageController {


    @Autowired
    private VoyageService vs ; 

    @PostMapping(value="/create")
    public ResponseEntity<?>  create(@RequestBody VoyageDTO voyage){
        var v = this.vs.create(voyage);
        return new ResponseEntity<>(v , HttpStatus.CREATED) ; 
    }

    @GetMapping(value = "/getAll")
    public  ResponseEntity<?> getAll(){

        List<VoyageDTO> listVoyage = this.vs.getAll() ;
        return new ResponseEntity<>( listVoyage , HttpStatus.OK) ; 
    }

    @GetMapping(value="/get/{idVoyage}")
    public ResponseEntity<?> get(@PathVariable Long idVoyage){
         VOYAGE v = this.vs.get(idVoyage);
        return new ResponseEntity<>(v , HttpStatus.FOUND);
    }

    @DeleteMapping(value="/delete/{idVoyage}")
    public ResponseEntity<?> delete(@PathVariable Long idVoyage){
        var response = this.vs.delete(idVoyage);
        return new ResponseEntity<>(response , HttpStatus.OK) ; 
    }


    @PutMapping(value="/update/{idVoyage}")
    public ResponseEntity<?> update(  @PathVariable Long idVoyage ,   @RequestBody   VoyageDTO voyage){
        var v = this.vs.update(idVoyage ,  voyage);
        return new ResponseEntity<>(v , HttpStatus.OK);
    }

    @GetMapping(path="/recent")
    public ResponseEntity<?> recent(){
        return new ResponseEntity<>(this.vs.recent() ,HttpStatus.OK ) ; 
    }


    @GetMapping("/available")
    public  List<VoyageDTO>  voyageDisponible() {
        return this.vs.voyageDisponible() ; 
    }


    @PutMapping("/client/research")
    public  List<VoyageDTO> gresearchForClient( 
        @RequestBody  VoyageDTO  dto
    ) {
        return  this.vs.researchForClient(
            dto.getDateVoyage() ,
            dto.getDepartVoyage(),
            dto.getArriveVoyage() 
        ) ; 
    }

    @PutMapping("/agent/research")
    public  List<VoyageDTO> gresearchForAgent(
       
        @RequestBody  VoyageDTO dto
    ) {
        return  this.vs.researchForAgent( 
            dto.getDateVoyage() ,
            dto.getDepartVoyage(),
            dto.getArriveVoyage() 
        ) ; 
    }


    @GetMapping(path="/agent/averageRecentVoyage")
    public ResponseEntity<?> averageRecentVoyage(){
        var response = this.vs.recentVoyageAverage() ; 
        Map<String , Object> o  = new HashMap<>() ;
        
        o.put( "average", response) ; 
        
        return new ResponseEntity<>(o , HttpStatus.OK) ; 
    }
     
    @GetMapping(path="/agent/volReserver")
    public ResponseEntity<?> volReserver(){
        var response = this.vs.volReserver() ; 
        Map<String , Object> o  = new HashMap<>() ;
        
        o.put( "value", response) ; 
        
        return new ResponseEntity<>(o , HttpStatus.OK) ; 
    }   



    @GetMapping(value="/agent/departs")
    public List<String>AllDepart(){
        return this.vs.AllDepart() ; 

    }
    
    @GetMapping(value="/agent/arrivees")
    public List<String>AllArrivee(){
        return this.vs.AllArrive() ; 

    }
    

    @GetMapping(value="/client/arrivees")
    public List<String> AllArriveeDispo(){
        return this.vs.AllArriveeDisponible() ;
    }

    @GetMapping(value = "/client/departs")
    public List<String> AllDepartDispo(){
        return this.vs.AllDepartDisponible() ; 
    }


    @GetMapping(path="/top3")
    public ResponseEntity<?> voyageTop3(){
        return  new ResponseEntity<>(this.voyageTop3() , HttpStatus.OK);

    }
     
    
}
