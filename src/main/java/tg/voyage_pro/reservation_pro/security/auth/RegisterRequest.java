
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

  
    private String  uername ;
    private String password ;
    private Roles role ;

}
