package tg.voyage_pro.reservation_pro.core;


import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;

import tg.voyage_pro.reservation_pro.Model.PAIEMENT;
import tg.voyage_pro.reservation_pro.database.AgentRepository;
import tg.voyage_pro.reservation_pro.database.PaiementRepository;
import tg.voyage_pro.reservation_pro.database.ReservationRepository;
import tg.voyage_pro.reservation_pro.dto.PaiementDTO;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository  repo ;

    @Autowired
    private AgentRepository ar ;

    @Autowired
    private ReservationRepository rr ;


     public PAIEMENT makePaiement(PaiementDTO paiement){
        String uuid = UUID.randomUUID().toString() ; 

        var agent = this.ar.findById(paiement.getIdAgent())
        .orElseThrow(()-> new RuntimeException("agent not found")) ;

        var reservation  = this.rr.findById(paiement.getIdReservation())
        .orElseThrow(()-> new RuntimeException("Reservation not found"));


        return this.repo.save(PAIEMENT.builder()
            .agent(agent)
            .codePaiement(uuid)
            .reservation(reservation)
            .datePaiement(paiement.getDatePaiement())
            .build()
        ) ; 



         
    }


    public List<PaiementDTO> getAllPaiment(){
        return this.repo.findAll().stream().map(x->
            PaiementDTO.builder()
            .idAgent(x.getAgent().getIdAgent())
            .idReservation(x.getReservation().getIdReservation())
            .codePaiement(x.getCodePaiement())
            .datePaiement(x.getDatePaiement())
            .build()
        ).collect(Collectors.toList()) ; 
    }


    public boolean deletePaiement(String codePaiement){
        var paiement = this.repo.findById(codePaiement).orElse(null) ;
        if(paiement == null) return false ; 
        this.repo.deleteById(codePaiement);
        return true ; 
    }

}
