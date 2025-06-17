package tg.voyage_pro.reservation_pro.core;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import tg.voyage_pro.reservation_pro.Model.CLIENT;
import tg.voyage_pro.reservation_pro.Model.RESERVATION;
import tg.voyage_pro.reservation_pro.Model.STATUS;
import tg.voyage_pro.reservation_pro.Model.TYPE_BILLET;
import tg.voyage_pro.reservation_pro.Model.VOYAGE;
import tg.voyage_pro.reservation_pro.database.*;
import tg.voyage_pro.reservation_pro.dto.ReservationDTO;
import tg.voyage_pro.reservation_pro.exceptions.ClientNotFoundException;
import tg.voyage_pro.reservation_pro.exceptions.NullValueException;
import tg.voyage_pro.reservation_pro.exceptions.ReservationNotFoundException;
import tg.voyage_pro.reservation_pro.exceptions.VoyageNotFoundException;
import tg.voyage_pro.reservation_pro.status_reservation.StatusMediator;

@Service
public class ReservationService {


    @Autowired
    private ReservationRepository repoReservation ;

    @Autowired
    private ClientRepository cr ; 

    @Autowired
    private VoyageRepository repoVoyage ; 

    @Autowired
    private TypeBilletRepository repoTypeBillet;

    public   ReservationDTO create(ReservationDTO reservation){

       CLIENT client = this.cr.findByMailClient(reservation.getMailClient()).orElseThrow(()-> new NullValueException("Either client is not found or null value")) ; 
       VOYAGE voyage = this.repoVoyage.findById( reservation.getIdVoyage()).orElseThrow(()-> new NullValueException( "Either voyage is not found or null value"));
       TYPE_BILLET type = this.repoTypeBillet.findById(reservation.getIdTypeBillet()).orElseThrow( ()-> new NullValueException("Either type is not found or null value"));

       if(reservation.getDateReservation() == null){
            throw new NullValueException("Date must not be null") ; 
       }

       Integer nbrPlace = voyage.getNbrPlaceDisponible() - reservation.getNbrPlace() ; 

       if(nbrPlace < 0 ) return null ; 

        voyage.setNbrPlaceDisponible(nbrPlace);
        this.repoVoyage.save(voyage) ; 


        var  r =   this.repoReservation.save(
            RESERVATION.builder()
            .client(client)
            .voyage(voyage)
            .typeBillet(type)
            .nbrPlace(reservation.getNbrPlace())
            .montant(type.getPrixTypeBillet()   * reservation.getNbrPlace())
            .status(STATUS.EN_ATTENTE_CONFIRMATION)
            .dateReservation(reservation.getDateReservation())
        
            .build()
        );

        return  ReservationDTO.builder()

        .idClient(r.getClient().getIdClient())
        .idVoyage(r.getVoyage().getIdVoyage())
        .idReservation(r.getIdReservation())
        .nomClient( r.getClient().getNomClient())
        .libelleVoyage(r.getVoyage().getDepartVoyage() +" - "+ r.getVoyage().getArriveVoyage())
        .depart(r.getVoyage().getDepartVoyage())
        .arrivee(r.getVoyage().getArriveVoyage())
        .mailClient(r.getClient().getMailClient())
        .dateReservation(r.getDateReservation()) 
        .idTypeBillet(r.getTypeBillet().getIdTypeBillet())
        .libelleTypeBillet(r.getTypeBillet().getLibelleTypeBillet())
        .montant(r.getMontant())
        .build() ; 
       
        

        
    }

    public boolean delete(Long IdReservation){

        if(!this.repoReservation.existsById(IdReservation)){
            return false ;
        }
        
        this.repoReservation.deleteById(IdReservation);
        return true ; 
    }


    
    
    public List<ReservationDTO> getAll(){
        return this.repoReservation.findAll().stream().map( r->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                .idReservation(r.getIdReservation())
                .nomClient( r.getClient().getNomClient())
                .libelleVoyage(r.getVoyage().getDepartVoyage() +" - "+ r.getVoyage().getArriveVoyage())
                .depart(r.getVoyage().getDepartVoyage())
                .arrivee(r.getVoyage().getArriveVoyage())
                .mailClient(r.getClient().getMailClient())
                .dateReservation(r.getDateReservation()) 
                .status(r.getStatus())
                .nbrPlace(r.getNbrPlace())
                .idTypeBillet(r.getTypeBillet().getIdTypeBillet())
                .libelleTypeBillet(r.getTypeBillet().getLibelleTypeBillet())
                .montant(r.getMontant())
                .build()
        ).collect(Collectors.toList());
    }
    public List<ReservationDTO> getAllForOne(String email){
        return this.repoReservation.findAllForOne(email).stream().map( r->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                .idReservation(r.getIdReservation())
                .nomClient( r.getClient().getNomClient())
                .libelleVoyage(r.getVoyage().getDepartVoyage() +" - "+ r.getVoyage().getArriveVoyage())
                .depart(r.getVoyage().getDepartVoyage())
                .arrivee(r.getVoyage().getArriveVoyage())
                .mailClient(r.getClient().getMailClient())
                .dateReservation(r.getDateReservation()) 
                .idTypeBillet(r.getTypeBillet().getIdTypeBillet())
                .libelleTypeBillet(r.getTypeBillet().getLibelleTypeBillet())
                .status(r.getStatus())
                .nbrPlace(r.getNbrPlace())
                .montant(r.getMontant())
                .build()
        ).collect(Collectors.toList());
    }

    public ReservationDTO get(Long idReservation){
        RESERVATION r =  this.repoReservation.findById(idReservation).orElseThrow(()-> new RuntimeException("Reservation not found")) ; 
        
        return ReservationDTO.builder()
            .idClient(r.getClient().getIdClient())
            .idVoyage(r.getVoyage().getIdVoyage())
            .idReservation(r.getIdReservation())
            .nomClient( r.getClient().getNomClient())
            .libelleVoyage(r.getVoyage().getDepartVoyage() +" - "+ r.getVoyage().getArriveVoyage())
            .depart(r.getVoyage().getDepartVoyage())
            .arrivee(r.getVoyage().getArriveVoyage())
            .mailClient(r.getClient().getMailClient())
            .dateReservation(r.getDateReservation()) 
            .status(r.getStatus())
            .nbrPlace(r.getNbrPlace())
            .idTypeBillet(r.getTypeBillet().getIdTypeBillet())
            .libelleTypeBillet(r.getTypeBillet().getLibelleTypeBillet())
            .montant(r.getMontant())
            .build() ; 
    }


    public ReservationDTO update(ReservationDTO r){
        RESERVATION res =  this.repoReservation.findById(r.getIdReservation()).orElseThrow(()-> new  ReservationNotFoundException("Reservation not found")) ; 

        VOYAGE v = this.repoVoyage.findById(r.getIdVoyage()).orElseThrow(()-> new VoyageNotFoundException("voyage not found"));

        CLIENT c = this.cr.findById(r.getIdClient()).orElseThrow(()-> new ClientNotFoundException("Client not found"));

        res.setDateReservation(r.getDateReservation());
        res.setClient(c);
        res.setVoyage(v);

        this.repoReservation.save(res) ; 


        return ReservationDTO.builder()
            .idClient(c.getIdClient())
            .idReservation(res.getIdReservation())
            .idVoyage(v.getIdVoyage())
            .nomClient( res.getClient().getNomClient())
            .libelleVoyage(res.getVoyage().getDepartVoyage() +" - "+ res.getVoyage().getArriveVoyage())
            .mailClient(res.getClient().getMailClient())
            .depart(v.getDepartVoyage())
            .arrivee(v.getArriveVoyage())
            .montant(res.getMontant())
            .status(r.getStatus())
            .nbrPlace(r.getNbrPlace())
            .dateReservation(res.getDateReservation()) 
            .idTypeBillet(res.getTypeBillet().getIdTypeBillet())
            .libelleTypeBillet(res.getTypeBillet().getLibelleTypeBillet())
            .build() ; 




    }

    public Integer nbrReservationRecente(){
        return  this.repoReservation.reservations() ;  
    }

    public String confirmer(Long idReservation ){
        var reservation = this.repoReservation.findById(idReservation).orElseThrow(()-> new ReservationNotFoundException("reservation not found") ) ;
        
        reservation = StatusMediator.builder().build().Confirmer(reservation) ; 

        this.repoReservation.save(reservation) ; 

        return "Operation réussie" ; 
    }

    public  String payee(Long idReservation){
        var reservation = this.repoReservation.findById(idReservation).orElseThrow(()-> new ReservationNotFoundException("reservation not found") ) ;
        
        reservation = StatusMediator.builder().build().Payee(reservation) ; 

        this.repoReservation.save(reservation) ; 

        return "Operation réussie" ; 
    }

  
    public String  annulee(Long idReservation){
        var reservation = this.repoReservation.findById(idReservation).orElseThrow(()-> new ReservationNotFoundException("reservation not found") ) ;
        
        reservation = StatusMediator.builder().build().Annulee(reservation) ; 

        this.repoReservation.save(reservation) ; 

        return "Operation réussie" ; 
    }



    public List<ReservationDTO> research(ReservationDTO res){

        return this.repoReservation.researchForOne(
            res.getMailClient() , 
            res.getDepart(),
            res.getArrivee(), 
            res.getDateReservation().toString()
        ).stream().map(r ->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                .idReservation(r.getIdReservation())
                .nomClient( r.getClient().getNomClient())
                .libelleVoyage(r.getVoyage().getDepartVoyage() +" - "+ r.getVoyage().getArriveVoyage())
                .depart(r.getVoyage().getDepartVoyage())
                .arrivee(r.getVoyage().getArriveVoyage())
                .mailClient(r.getClient().getMailClient())
                .dateReservation(r.getDateReservation()) 
                .idTypeBillet(r.getTypeBillet().getIdTypeBillet())
                .libelleTypeBillet(r.getTypeBillet().getLibelleTypeBillet())
                .status(r.getStatus())
                .nbrPlace(r.getNbrPlace())
                .montant(r.getMontant())
                .build()
        
        ).collect(Collectors.toList());

         
           

    }


    
   
}
