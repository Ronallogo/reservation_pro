package tg.voyage_pro.reservation_pro.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import tg.voyage_pro.reservation_pro.Model.CLIENT;
import tg.voyage_pro.reservation_pro.Model.VOYAGE;

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
    private String libelleVoyage; 
    private String nomClient ; 
    private String prenomClient ; 
    private String mailClient ; 
    private Date dateReservation ; 
}
