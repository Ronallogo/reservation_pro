package tg.voyage_pro.reservation_pro.security.token;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/tg/voyage_pro/reservation/auth//token")
@CrossOrigin(origins = "*")
public class TokenController {

    @Autowired
    private tokenService tokenService ; 

    @PostMapping(value="/check" , consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean checkValidity(@RequestBody TokenRequest token){
            return this.tokenService.checkValidity(token.getToken());
    }

    
}
