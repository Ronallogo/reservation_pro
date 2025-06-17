
package tg.voyage_pro.reservation_pro.Security.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tg.voyage_pro.reservation_pro.Security.entities.Roles;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  
    private String  username ;
    private String password ;
    private String lastname ; 
    private String firstname ; 
    private String sexe ; 
    private String phone ; 
    private String email   ; 
    private Roles role ;

}
