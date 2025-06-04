package tg.voyage_pro.reservation_pro.Security.auth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.Security.entities.User;
import tg.voyage_pro.reservation_pro.Security.entities.UserResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

    private final AuthenticationService service ;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }



  /*   @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
       // service.refreshToken(request, response);
    }*/

    @GetMapping("/allUser")
    public List<User> all(){
       // return this.service.getAllUser();
       return null ; 
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<UserResponse> findUser(@PathVariable String email){
       // var e =  this.service.findUser(email) ;
        return null;
    }
    @GetMapping("/validity")
    public ResponseEntity<?> findUser(@RequestBody  AuthenticationResponse request){
        var e =  this.service.checkValidityToken(request);
        return ResponseEntity.ok(e);
    }

    @PutMapping("/editWithPassword/{id}")
    public ResponseEntity<AuthenticationResponse> editUser(@PathVariable Long id ,    @RequestBody RegisterRequest request){
           // var e =  this.service.editUserWithPassword(id , request);
            return  null ; 
    }

    @PutMapping("/editWithoutPassword/{id}")
    public ResponseEntity<AuthenticationResponse> editUser(@PathVariable Long id ,    @RequestBody UserResponse request){
       // var e =  this.service.editUserWithoutPassword(id , request);
        return  null ; 
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id){
        var e =  this.service.delete(id);
        return ResponseEntity.ok(e);
    }







}
