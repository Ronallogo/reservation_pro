package tg.voyage_pro.reservation_pro.controllers;

import java.util.HashMap;
import java.util.Map;

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

import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.core.PaiementService;
import tg.voyage_pro.reservation_pro.dto.PaiementDTO;

@RestController
@RequestMapping(path="/tg/voyage_pro/reservation/auth/paiement")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService service ; 


    @PostMapping(path="/makePaiement")
    public  ResponseEntity<?> makePaiement(@RequestBody PaiementDTO paiement){
        var  response = this.service.makePaiement(paiement) ; 
        return new ResponseEntity<>( response , HttpStatus.OK) ; 
    }

    @GetMapping(path="/all")
    public  ResponseEntity<?> getAll(){
        var response = this.service.getAllPaiment() ; 
        return new ResponseEntity<>(response , HttpStatus.OK) ; 
    }

    @DeleteMapping(path="/delete/{code}")
    public ResponseEntity<?> delete(@PathVariable String code){
        var response = this.service.deletePaiement(code);

        return new ResponseEntity<>(response , HttpStatus.OK) ;
    }

    @GetMapping(path = "/getAllForOne/{email}")
    public ResponseEntity<?> getAllForOne(@PathVariable String email){
        var  response = this.service.getAllForOne(email);

        return new ResponseEntity<>(response , HttpStatus.OK) ; 
    }

    @PutMapping(path="/research")
    public ResponseEntity<?> research(@RequestBody PaiementDTO dto){
        var response  = this.service.research(dto);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PutMapping(path="/researchForOne")
    public ResponseEntity<?> researchForOne(@RequestBody PaiementDTO dto){
        var response  = this.service.researchForOne(dto);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }


    @GetMapping(path="/newPaiementAverage")
    public ResponseEntity<?>  newPaiementAverage(){
        var response = this.service.paiementRecent() ; 
        Map<String , Object> o = new HashMap<>() ; 
        o.put("value" , response) ; 

        return new ResponseEntity<>( o , HttpStatus.OK); 
    }

   

    




}
