package  tg.voyage_pro.reservation_pro.Security.auth    ;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tg.voyage_pro.reservation_pro.Security.entities.User;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private  String accessToken ;
    @JsonProperty("refresh_token")
    private String refreshToken ;
    private User user ; 

}
