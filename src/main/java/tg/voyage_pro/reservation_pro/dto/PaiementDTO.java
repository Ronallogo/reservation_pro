package tg.voyage_pro.reservation_pro.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
import java.util.Date;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaiementDTO {



    private  String codePaiement ;

    private  Long idReservation ;

    private  Long idAgent ;
   
    private String mailAgentAssocie ; 
    private Float montant ; 
    private String libelleVoyage ; 
    private String libelleTypeBillet ; 
    private String nomClient ;
    private String prenomClient ;
    private String nomAgent ;
    private String telAgent ;
     




    private String prenomAgent ;
     
    private String mailClient ;
    private String telClient ;
    private String sexeClient ;
     private  String depart ; 
    private String arrivee ; 
    @JsonFormat(shape = JsonFormat.Shape.STRING  , pattern = "yyyy-MM-dd" , timezone = "UTC")
    private Date dateReservation ; 
    private Integer nbrPlace ; 

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date dateVoyage ;


 
    @JsonFormat(shape = JsonFormat.Shape.STRING  , pattern = "yyyy-MM-dd" , timezone = "UTC")
    private Date datePaiement ;

}
