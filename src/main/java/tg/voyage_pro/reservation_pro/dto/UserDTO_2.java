package tg.voyage_pro.reservation_pro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO_2 {

    private String firstname ; 
    private String lastname ; 
    private String email ; 
    private String phone ; 
}
