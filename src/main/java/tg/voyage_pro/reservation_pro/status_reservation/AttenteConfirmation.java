package tg.voyage_pro.reservation_pro.status_reservation;

import lombok.Builder;
import tg.voyage_pro.reservation_pro.Model.RESERVATION;
import tg.voyage_pro.reservation_pro.Model.STATUS;


@Builder
public class AttenteConfirmation  implements StatusService{

    @Override
    public RESERVATION MAKE_CANCEL(RESERVATION r) {
       r.setStatus(STATUS.ANNULEE);
       return r ; 
    }

    @Override
    public RESERVATION MAKE_PAID(RESERVATION r) {
        throw new RuntimeException("Veuillez attendre une confirmation !!");
    }

    @Override
    public RESERVATION MAKE_CONFIRMED(RESERVATION r) {
       r.setStatus(STATUS.EN_ATTENTE_PAIEMENT);
       return r ; 
    }

}
