package tg.voyage_pro.reservation_pro.status_reservation;

import lombok.Builder;
import tg.voyage_pro.reservation_pro.Model.RESERVATION;

@Builder

public class Payee  implements StatusService{

    @Override
    public RESERVATION MAKE_CANCEL(RESERVATION r) {
        // TODO Auto-generated method stub
        throw new RuntimeException("Cette reservation est déjà payée") ; 
    }

    @Override
    public RESERVATION MAKE_PAID(RESERVATION r) {
        throw new RuntimeException("Cette reservation est déjà payée") ; 
       
    }

    @Override
    public RESERVATION MAKE_CONFIRMED(RESERVATION r) {
        throw new RuntimeException("Cette reservation est déjà payée") ; 
    }

}
