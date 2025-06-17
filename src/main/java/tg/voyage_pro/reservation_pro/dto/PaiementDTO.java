package tg.voyage_pro.reservation_pro.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tg.voyage_pro.reservation_pro.Model.AGENT;
import tg.voyage_pro.reservation_pro.Model.RESERVATION;

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

 
    @JsonFormat(shape = JsonFormat.Shape.STRING  , pattern = "yyyy-MM-dd" , timezone = "UTC")
    private Date datePaiement ;

}
