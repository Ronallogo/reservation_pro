package tg.voyage_pro.reservation_pro.voyage;

import java.util.List;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping(value = "/tg/voyage_pro/reservation/auth/voyage")
@CrossOrigin("*")
public class VoyageController {


    @Autowired
    private VoyageService vs ; 

    @PostMapping(value="/create")
    public ResponseEntity<?>  create(@RequestBody VoyageDTO voyage){
        VoyageDTO v = this.vs.create(voyage);
        return new ResponseEntity<>(v , HttpStatus.CREATED) ; 
    }

    @GetMapping(value = "/getAll")
    public  ResponseEntity<?> getAll(){
        List<VoyageDTO> listVoyage = this.vs.getAll() ; 
        return new ResponseEntity<>( listVoyage , HttpStatus.OK) ; 
    }
}
