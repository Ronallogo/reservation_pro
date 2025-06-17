package tg.voyage_pro.reservation_pro.status_reservation;

import tg.voyage_pro.reservation_pro.Model.RESERVATION;

public interface StatusService {
    public  RESERVATION  MAKE_CANCEL(RESERVATION r) ; 


    public RESERVATION MAKE_PAID(RESERVATION r) ; 

    public RESERVATION MAKE_CONFIRMED(RESERVATION r) ;

    



}
