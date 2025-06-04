package  tg.voyage_pro.reservation_pro.Security.entities;


 
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter

public class UserResponse {
    private  Long id ;
    private String  username;
    private String role ;
}
