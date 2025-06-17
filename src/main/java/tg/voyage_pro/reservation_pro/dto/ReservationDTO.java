package tg.voyage_pro.reservation_pro.dto;

import java.sql.Date;

 
import lombok.*;
import tg.voyage_pro.reservation_pro.Model.STATUS;
 

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReservationDTO {
    private Long idReservation ; 
    private Long idClient ; 
    private Long idVoyage ; 
    private Long idTypeBillet ; 
    private  String libelleTypeBillet ; 
    private String libelleVoyage; 
    private String nomClient ; 
    private String prenomClient ; 
    private  String depart ; 
    private String arrivee ; 
    private   Float montant ; 
    private String mailClient ; 
    private Date dateReservation ; 
    private Integer nbrPlace ; 


    private STATUS status ; 
}
