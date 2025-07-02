package tg.voyage_pro.reservation_pro.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.Tuple;

import tg.voyage_pro.reservation_pro.Model.CLIENT;
import tg.voyage_pro.reservation_pro.Model.RESERVATION;
import tg.voyage_pro.reservation_pro.Model.STATUS;
import tg.voyage_pro.reservation_pro.Model.TYPE_BILLET;
import tg.voyage_pro.reservation_pro.Model.VOYAGE;
import tg.voyage_pro.reservation_pro.database.*;
import tg.voyage_pro.reservation_pro.dto.ReservationDTO;
import tg.voyage_pro.reservation_pro.dto.StatReservationObject_1;
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
    private AgentRepository repoAgent ; 

 

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
                .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
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
                .mailAgentAssocie(r.getMailAgentAssocie() )
                .idReservation(r.getIdReservation())
                 .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
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
            .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
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
        return  this.repoReservation.reservations().intValue()  ;  
    }

    public String confirmer(Long idReservation , String emailAgent ){
        var reservation = this.repoReservation.findById(idReservation).orElseThrow(()-> new ReservationNotFoundException("reservation not found") ) ;
        var agent = this.repoAgent.findByMailAgent(emailAgent).orElseThrow(()-> new RuntimeException("Agent not found")) ; 
        reservation = StatusMediator.builder().build().Confirmer(reservation) ; 

        reservation.setMailAgentAssocie(agent.getMailAgent());



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
        var voyage = this.repoVoyage.findById(reservation.getVoyage().getIdVoyage())
            .orElseThrow(()-> new RuntimeException( "Voyage nto found"));
        
        
        voyage.setNbrPlaceDisponible(reservation.getNbrPlace() + voyage.getNbrPlaceDisponible());
        this.repoVoyage.save(voyage) ; 

        this.repoReservation.save(reservation) ; 

        return "Operation réussie" ; 
    }

    public List<ReservationDTO> researchForOne(ReservationDTO res){

        return this.repoReservation.researchForOne(
            res.getMailClient() , 
            res.getDepart(),
            res.getArrivee(), 
            res.getDateReservation().toString()
        ).stream().map(r ->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                  .mailAgentAssocie(r.getMailAgentAssocie())
                .idReservation(r.getIdReservation())
               .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
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
    
    
    public List<ReservationDTO> researchForOneAnnulee(ReservationDTO res){

        return this.repoReservation.researchForOneAnnulee(
            res.getMailClient() , 
            res.getDepart(),
            res.getArrivee(), 
            res.getDateReservation().toString()
        ).stream().map(r ->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                  .mailAgentAssocie(r.getMailAgentAssocie())
                .idReservation(r.getIdReservation())
               .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
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
    
    
    public List<ReservationDTO> researchForOneEncours(ReservationDTO res){

        return this.repoReservation.researchForOneEnCours(
            res.getMailClient() , 
            res.getDepart(),
            res.getArrivee(), 
            res.getDateReservation().toString()
        ).stream().map(r ->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                  .mailAgentAssocie(r.getMailAgentAssocie())
                .idReservation(r.getIdReservation())
               .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
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
    
    public List<ReservationDTO> research(ReservationDTO res){

        return this.repoReservation.research(
           
            res.getDepart(),
            res.getArrivee(), 
            res.getDateReservation().toString()
        ).stream().map(r ->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                  .mailAgentAssocie(r.getMailAgentAssocie())
                .idReservation(r.getIdReservation())
               .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
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
    public List<ReservationDTO> researchAnnulee(ReservationDTO res){

        return this.repoReservation.researchAnnulee(
           
            res.getDepart(),
            res.getArrivee(), 
            res.getDateReservation().toString()
        ).stream().map(r ->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                  .mailAgentAssocie(r.getMailAgentAssocie())
                .idReservation(r.getIdReservation())
               .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
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
    public List<ReservationDTO> researchEncour(ReservationDTO res){

        return this.repoReservation.researchEncours(
           
            res.getDepart(),
            res.getArrivee(), 
            res.getDateReservation().toString()
        ).stream().map(r ->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                  .mailAgentAssocie(r.getMailAgentAssocie())
                .idReservation(r.getIdReservation())
               .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
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

    public Float reservationsRecent(){
        Float nbrTotalReservation = this.repoReservation.countReservation() ; 

        List<Float> reservationRecent = this.repoReservation.newReservations() ; 

        System.out.print(reservationRecent);
        System.out.println(nbrTotalReservation);
         

        return 100 - ((reservationRecent.size() * 100) / nbrTotalReservation) ; 
    }

    public Float reservationParclient(){
        return this.repoReservation.averageParClient() ; 
    }

    public List<ReservationDTO> getReservationsAnnulee(){
            return this.repoReservation.reservationAnnulee().stream().map( r->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                .mailAgentAssocie(r.getMailAgentAssocie())
                .idReservation(r.getIdReservation())
          .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
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

    public List<ReservationDTO> getReservationsAnnuleeForOne(String email){
            return this.repoReservation.reservationAnnuleeForOne(email).stream().map( r->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                .mailAgentAssocie(r.getMailAgentAssocie())
                .idReservation(r.getIdReservation())
                 .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
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

    public List<ReservationDTO> getReservationsEnCoursForOne(String email){
            return this.repoReservation.reservationEnCoursForOne(email).stream().map( r->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                  .mailAgentAssocie(r.getMailAgentAssocie())
                .idReservation(r.getIdReservation())
                .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
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
    public List<ReservationDTO> getReservationsEnCours( ){
            return this.repoReservation.reservationEncours().stream().map( r->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                .idReservation(r.getIdReservation())
                .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
                .libelleVoyage(r.getVoyage().getDepartVoyage() +" - "+ r.getVoyage().getArriveVoyage())
                .depart(r.getVoyage().getDepartVoyage())
                .arrivee(r.getVoyage().getArriveVoyage())
                .mailClient(r.getClient().getMailClient())
                .mailAgentAssocie(r.getMailAgentAssocie())
                .dateReservation(r.getDateReservation()) 
                .idTypeBillet(r.getTypeBillet().getIdTypeBillet())
                .libelleTypeBillet(r.getTypeBillet().getLibelleTypeBillet())
                .status(r.getStatus())
                .nbrPlace(r.getNbrPlace())
                .montant(r.getMontant())
                .build()
        ).collect(Collectors.toList());
    }

    public List<ReservationDTO> getReservationsForOne(String email){
            return this.repoReservation.reservationEffectuerForOne(email).stream().map( r->
                ReservationDTO.builder()
                .idClient(r.getClient().getIdClient())
                .idVoyage(r.getVoyage().getIdVoyage())
                .idReservation(r.getIdReservation())
                .mailAgentAssocie(r.getMailAgentAssocie())
                
                .nomClient( r.getClient().getNomClient() + " "+  r.getClient().getPrenomClient()  )
                .libelleVoyage(r.getVoyage().getDepartVoyage() +" - "+ r.getVoyage().getArriveVoyage())
                .depart(r.getVoyage().getDepartVoyage())
                .arrivee(r.getVoyage().getArriveVoyage())
                .mailAgentAssocie(r.getMailAgentAssocie())
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

    public StatReservationObject_1 data(Integer mois){
        Map<String , Object> result =  this.repoReservation.getDataDashBoard1(mois) ; 
        return StatReservationObject_1.builder()
        .gainMoyen((Double) result.get("gainMoyen"))
        .revenuTotal(( Double) result.get("revenuTotal"))
        .build() ; 


    }


    public   Map<String , Object> newEarnPourcentage(){
         return this.repoReservation.newEarnPourcentage() ;
         
         
    }

    public Map<String  , Object> TotalEarn(){
            return this.repoReservation.gainTotal() ; 
    }

    public Map<String , Object> revenuMoyen(){
        Map<String , Object> response = new  HashMap<String  , Object>() ; 
        var value = this.repoReservation.revenuMoyen() ; 

        response.put( "value",  value);
        return response ; 
    }
    public Map<String , Object>  tauxAnnulation(){
        Map<String , Object> response = new  HashMap<String  , Object>() ; 
        var value = this.repoReservation.tauxAnnulation() ; 

        response.put( "value",  value);
        return response ; 
    }

    

    
 


    
   
}
