package tg.voyage_pro.reservation_pro.core;


import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;

import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.Model.PAIEMENT;
import tg.voyage_pro.reservation_pro.database.AgentRepository;
import tg.voyage_pro.reservation_pro.database.PaiementRepository;
import tg.voyage_pro.reservation_pro.database.ReservationRepository;
import tg.voyage_pro.reservation_pro.dto.PaiementDTO;
import tg.voyage_pro.reservation_pro.dto.StatReservationObject_1;
import tg.voyage_pro.reservation_pro.status_reservation.StatusMediator;

@Service
@RequiredArgsConstructor
public class PaiementService {

    @Autowired
    private PaiementRepository  repo ;

    @Autowired
    private AgentRepository ar ;

    @Autowired
    private ReservationRepository rr ;

   
     


     public  PaiementDTO makePaiement(PaiementDTO paiement){
        String uuid = UUID.randomUUID().toString() ; 

        var agent = this.ar.findByMailAgent(paiement.getMailAgentAssocie())
        .orElseThrow(()-> new RuntimeException("agent not found")) ;

        var reservation  = this.rr.findById(paiement.getIdReservation())
        .orElseThrow(()-> new RuntimeException("Reservation not found"));

        reservation =  StatusMediator.builder().build().Payee(reservation) ; 
        this.rr.save( reservation);


        this.repo.save(PAIEMENT.builder()
            .agent(agent)
            .codePaiement(uuid)
            .reservation(reservation)
            .datePaiement(paiement.getDatePaiement())
            .build()
        ) ; 
        reservation =    StatusMediator.builder().build().Payee(reservation) ; 

        this.rr.save(reservation) ; 
        
        return  PaiementDTO.builder()
            .codePaiement(uuid)
            .datePaiement(paiement.getDatePaiement())
            .idAgent(agent.getIdAgent())
            .idReservation(reservation.getIdReservation())
            .mailAgentAssocie(agent.getMailAgent())
            .libelleVoyage(reservation.getVoyage().getDepartVoyage() +" "+ reservation.getVoyage().getArriveVoyage())
        .build() ; 



         
    }


    public List<PaiementDTO> getAllPaiment(){
        return this.repo.findAll().stream().map(x->
            PaiementDTO.builder()
            .telAgent(x.getAgent().getTelAgent())
            .telClient(x.getReservation().getClient().getTelClient())
            .codePaiement(x.getCodePaiement())
            .dateReservation(x.getReservation().getDateReservation())
            .libelleTypeBillet(x.getReservation().getTypeBillet().getLibelleTypeBillet())
            .arrivee(x.getReservation().getVoyage().getArriveVoyage())
            .depart(x.getReservation().getVoyage().getDepartVoyage())
            .mailClient(x.getReservation().getClient().getMailClient())
            .nomClient(x.getReservation().getClient().getNomClient() + " " + x.getReservation().getClient().getPrenomClient())
            .nomAgent(x.getAgent().getNomAgent() +" " + x.getAgent().getPrenomAgent())
            .nomClient(x.getReservation().getClient().getPrenomClient())
            .dateReservation(x.getReservation().getDateReservation())
            .dateVoyage(x.getReservation().getVoyage().getDateVoyage())
            .montant( x.getReservation().getMontant())
            .nbrPlace(x.getReservation().getNbrPlace())
            
            .datePaiement(x.getDatePaiement())
            .idAgent( x.getAgent().getIdAgent())
            .idReservation( x.getReservation().getIdReservation())
            .mailAgentAssocie( x.getAgent().getMailAgent())
            .libelleVoyage( x.getReservation().getVoyage().getDepartVoyage() +" "+ x.getReservation().getVoyage().getArriveVoyage())
            .build()
        ).collect(Collectors.toList()) ; 
    }


    public boolean deletePaiement(String codePaiement){
        var paiement = this.repo.findById(codePaiement).orElse(null) ;
        if(paiement == null) return false ; 
        this.repo.deleteById(codePaiement);
        return true ; 
    }

    public List<PaiementDTO> getAllForOne(String email){
        return this.repo.getAllForOne(email).stream().map(x->
            PaiementDTO.builder()
            .telAgent(x.getAgent().getTelAgent())
            .telClient(x.getReservation().getClient().getTelClient())
            .codePaiement(x.getCodePaiement())
            .dateReservation(x.getReservation().getDateReservation())
            .libelleTypeBillet(x.getReservation().getTypeBillet().getLibelleTypeBillet())
            .arrivee(x.getReservation().getVoyage().getArriveVoyage())
            .depart(x.getReservation().getVoyage().getDepartVoyage())
            .mailClient(x.getReservation().getClient().getMailClient())
            .nomClient(x.getReservation().getClient().getNomClient() + " " + x.getReservation().getClient().getPrenomClient())
            .nomAgent(x.getAgent().getNomAgent() +" " + x.getAgent().getPrenomAgent())
            .nomClient(x.getReservation().getClient().getPrenomClient())
            .dateReservation(x.getReservation().getDateReservation())
            .dateVoyage(x.getReservation().getVoyage().getDateVoyage())
            .montant( x.getReservation().getMontant())
            .nbrPlace(x.getReservation().getNbrPlace())
            
            .datePaiement(x.getDatePaiement())
            .idAgent( x.getAgent().getIdAgent())
            .idReservation( x.getReservation().getIdReservation())
            .mailAgentAssocie( x.getAgent().getMailAgent())
            .libelleVoyage( x.getReservation().getVoyage().getDepartVoyage() +" "+ x.getReservation().getVoyage().getArriveVoyage())
            .build()

        
        ).collect(Collectors.toList());




     
    }


    public List<PaiementDTO> researchForOne(PaiementDTO p){
        
            return this.repo.researchForOne(
                p.getDepart() , 
                p.getArrivee() ,
                p.getDateVoyage().toString() , 
                p.getMailClient()
            ).stream().map(x->
            PaiementDTO.builder()
            .telAgent(x.getAgent().getTelAgent())
            .telClient(x.getReservation().getClient().getTelClient())
            .codePaiement(x.getCodePaiement())
            .dateReservation(x.getReservation().getDateReservation())
            .libelleTypeBillet(x.getReservation().getTypeBillet().getLibelleTypeBillet())
            .arrivee(x.getReservation().getVoyage().getArriveVoyage())
            .depart(x.getReservation().getVoyage().getDepartVoyage())
            .mailClient(x.getReservation().getClient().getMailClient())
            .nomClient(x.getReservation().getClient().getNomClient() + " " + x.getReservation().getClient().getPrenomClient())
            .nomAgent(x.getAgent().getNomAgent() +" " + x.getAgent().getPrenomAgent())
            .nomClient(x.getReservation().getClient().getPrenomClient())
            .dateReservation(x.getReservation().getDateReservation())
            .dateVoyage(x.getReservation().getVoyage().getDateVoyage())
            .montant( x.getReservation().getMontant())
            .nbrPlace(x.getReservation().getNbrPlace())
            
            .datePaiement(x.getDatePaiement())
            .idAgent( x.getAgent().getIdAgent())
            .idReservation( x.getReservation().getIdReservation())
            .mailAgentAssocie( x.getAgent().getMailAgent())
            .libelleVoyage( x.getReservation().getVoyage().getDepartVoyage() +" "+ x.getReservation().getVoyage().getArriveVoyage())
            .build()

        
        ).collect(Collectors.toList());
       }
    public List<PaiementDTO> research(PaiementDTO p){
        
            return this.repo.research(
                p.getDepart() , 
                p.getArrivee() ,
                p.getDateVoyage().toString() 
             
            ).stream().map(x->
            PaiementDTO.builder()
            .telAgent(x.getAgent().getTelAgent())
            .telClient(x.getReservation().getClient().getTelClient())
            .codePaiement(x.getCodePaiement())
            .dateReservation(x.getReservation().getDateReservation())
            .libelleTypeBillet(x.getReservation().getTypeBillet().getLibelleTypeBillet())
            .arrivee(x.getReservation().getVoyage().getArriveVoyage())
            .depart(x.getReservation().getVoyage().getDepartVoyage())
            .mailClient(x.getReservation().getClient().getMailClient())
            .nomClient(x.getReservation().getClient().getNomClient() + " " + x.getReservation().getClient().getPrenomClient())
            .nomAgent(x.getAgent().getNomAgent() +" " + x.getAgent().getPrenomAgent())
            .nomClient(x.getReservation().getClient().getPrenomClient())
            .dateReservation(x.getReservation().getDateReservation())
            .dateVoyage(x.getReservation().getVoyage().getDateVoyage())
            .montant( x.getReservation().getMontant())
            .nbrPlace(x.getReservation().getNbrPlace())
            
            .datePaiement(x.getDatePaiement())
            .idAgent( x.getAgent().getIdAgent())
            .idReservation( x.getReservation().getIdReservation())
            .mailAgentAssocie( x.getAgent().getMailAgent())
            .libelleVoyage( x.getReservation().getVoyage().getDepartVoyage() +" "+ x.getReservation().getVoyage().getArriveVoyage())
            .build()

        
        ).collect(Collectors.toList());
       }
    
   

    public  Long paiementRecent(){
        var result =  this.repo.newPaiements() ; 
        return( (Long) result.get("nbr") )/ 100   ; 


    }
}
