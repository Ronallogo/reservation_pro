package tg.voyage_pro.reservation_pro.status_reservation;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.Model.RESERVATION;
import tg.voyage_pro.reservation_pro.Model.STATUS;
import tg.voyage_pro.reservation_pro.database.ReservationRepository;

@Builder
@RequiredArgsConstructor
public class AttentePaiement implements StatusService {
    @Override
    public RESERVATION   MAKE_CANCEL(RESERVATION r) {
        r.setStatus(STATUS.ANNULEE);
        return r ; 
    }

     

    @Override
    public RESERVATION MAKE_PAID(RESERVATION r) {
        r.setStatus(STATUS.EN_ATTENTE_CONFIRMATION);
        return r ; 
         
    }

    @Override
    public RESERVATION MAKE_CONFIRMED(RESERVATION r) {
        throw new RuntimeException("Cette reservation n'est pas encore payée") ; 
    }



  




 


    

}
