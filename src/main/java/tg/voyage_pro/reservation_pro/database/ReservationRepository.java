package tg.voyage_pro.reservation_pro.database;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tg.voyage_pro.reservation_pro.Model.RESERVATION;


@Repository
public interface ReservationRepository  extends JpaRepository<RESERVATION , Long>{


    @Query(value ="""
        SELECT COUNT(*) FROM reservation ; 
    """ , nativeQuery =  true)
    public Integer reservations() ; 


    @Query(value ="""
        SELECT COUNT(r.voyage_id), v.arrive_voyage   
        FROM reservation r 
        JOIN voyage v ON v.id_voyage = r.voyage_id 
        WHERE r.date_reservation >= CURRENT_DATE - INTERVAL '30 days'
        GROUP BY v.arrive_voyage;
    """ , nativeQuery =  true)
    public Integer newReservations();


  /*  @Query(value="""
    SELECT AVG(r.id_reservation) FROM reservation r 
    WHERE r.date_reservation >= CURRENT_DATE - INTERVAL '10 days
    """ , nativeQuery = true)
    Float AverageNewReservation();*/

    @Query(value ="""
        SELECT COUNT(r.voyage_id), v.arrive_voyage   
        FROM reservation r 
        JOIN voyage v ON v.id_voyage = r.voyage_id 
        WHERE r.date_reservation >= CURRENT_DATE - INTERVAL '10 days' 
         GROUP BY v.arrive_voyage    
        ORDER BY COUNT(r.voyage_id) DESC  LIMIT  3 ;
    """ , nativeQuery =  true)
    public Integer topReservations();




    @Query(value="""
            SELECT r.* FROM reservation r JOIN client c ON c.id_client = r.client_id
            WHERE  c.mail_client =  :email ; 
    """ , nativeQuery =  true)
    public List<RESERVATION> findAllForOne(@Param("email") String email) ; 



    @Query(value = """
    SELECT r.* 
    FROM reservation r 
    JOIN client c ON c.id_client = r.client_id 
    JOIN voyage v ON v.id_voyage = r.voyage_id
    WHERE 
    (:email IS NULL OR c.mail_client LIKE '%' || :email || '%')
    AND (:depart IS NULL OR v.depart_voyage LIKE '%' || :depart || '%')
    AND (:arrivee IS NULL OR v.arrive_voyage LIKE '%' || :arrivee || '%')
    AND (:dateReservation IS NULL OR TO_CHAR(r.date_reservation, 'YYYY-MM-DD') LIKE '%' || :dateReservation || '%')
    """ , nativeQuery = true)
    public List<RESERVATION> researchForOne(
        @Param("email") String email ,
        @Param("depart") String depart , 
        @Param("arrivee") String arrivee , 
        @Param("dateReservation")  String dateReservation

    ) ; 
    //(TO_CHAR(r.date_reservation, 'YYYY-MM-DD')
 

}
