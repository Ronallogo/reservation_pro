package tg.voyage_pro.reservation_pro.status_reservation;

 

import lombok.Builder;
import tg.voyage_pro.reservation_pro.Model.RESERVATION;

@Builder

public class StatusMediator {

    public  RESERVATION Confirmer(RESERVATION r){

        switch (r.getStatus().name()) {
            case  "EN_ATTENTE_PAIEMENT":
                return    AttenteConfirmation.builder().build().MAKE_CONFIRMED(r) ; 
            case "EN_ATTENTE_CONFIRMATION" :
                return AttenteConfirmation.builder().build().MAKE_CONFIRMED(r);

            case "PAYEE" : 
                return  Payee.builder().build().MAKE_CONFIRMED(r) ; 
            case "ANNULEE" : 
                return  Annulee.builder().build().MAKE_CONFIRMED(r) ;     
            default:
                return r ; 
                 
        }
        

       
        
    }

    
    public  RESERVATION Payee(RESERVATION r){

        switch (r.getStatus().name()) {
            case  "EN_ATTENTE_PAIEMENT":
                return    AttenteConfirmation.builder().build().MAKE_PAID(r) ; 
            case "EN_ATTENTE_CONFIRMATION" :
                return AttenteConfirmation.builder().build().MAKE_PAID(r);

            case "PAYEE" : 
                return  Payee.builder().build().MAKE_PAID(r) ; 
            case "ANNULEE" : 
                return  Annulee.builder().build().MAKE_PAID(r) ;     
            default:
                return r ; 
                 
        }

       
        
    }

    
    public  RESERVATION Annulee(RESERVATION r){

        switch (r.getStatus().name()) {
            case  "EN_ATTENTE_PAIEMENT":
                return    AttenteConfirmation.builder().build().MAKE_CANCEL(r) ; 
            case "EN_ATTENTE_CONFIRMATION" :
                return AttenteConfirmation.builder().build().MAKE_CANCEL(r);

            case "PAYEE" : 
                return  Payee.builder().build().MAKE_CANCEL(r) ; 
            case "ANNULEE" : 
                return  Annulee.builder().build().MAKE_CANCEL(r) ;     
            default:
                return r ; 
                 
        }

       
        
    }






}
