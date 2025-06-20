package tg.voyage_pro.reservation_pro.database;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.annotations.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.util.Tuple;

import tg.voyage_pro.reservation_pro.Model.CLIENT;
import tg.voyage_pro.reservation_pro.Model.RESERVATION;
import tg.voyage_pro.reservation_pro.dto.StatReservationObject_1;


@Repository
public interface ReservationRepository  extends JpaRepository<RESERVATION , Long>{


    @Query(value ="""
        SELECT COUNT(*) FROM reservation ; 
    """ , nativeQuery =  true)
    public  Float reservations() ; 


    @Query(value ="""
        SELECT COUNT(r.voyage_id)  
        FROM reservation r 
        JOIN voyage v ON v.id_voyage = r.voyage_id 
        WHERE r.date_reservation >= CURRENT_DATE - INTERVAL '30 days'
        GROUP BY v.arrive_voyage;
    """ , nativeQuery =  true)
    public List<Float> newReservations();


    @Query(value="""
        SELECT COUNT(*) FROM reservation ; 
    """ , nativeQuery =  true)
    public  Float countReservation() ; 


  /*  @Query(value="""
    SELECT AVG(r.id_reservation) FROM reservation r 
    WHERE r.date_reservation >= CURRENT_DATE - INTERVAL '10 days
    """ , nativeQuery = true)
    Float AverageNewReservation();*/

    @Query(value ="""
        SELECT COUNT(r.voyage_id)
        FROM reservation r 
        JOIN voyage v ON v.id_voyage = r.voyage_id 
        WHERE r.date_reservation >= CURRENT_DATE - INTERVAL '10 days' 
         GROUP BY v.arrive_voyage    
        ORDER BY COUNT(r.voyage_id) DESC  LIMIT  3 ;
    """ , nativeQuery =  true)
    public List<Integer> topReservations();




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
        (:email IS NULL OR c.mail_client = :email)
        AND (
            (:depart IS NULL OR REPLACE(v.depart_voyage, ' ', '') = REPLACE(:depart, ' ', ''))
            OR
            (:arrivee IS NULL OR v.arrive_voyage LIKE '%' || :arrivee || '%')
            OR
            (:dateReservation IS NULL OR TO_CHAR(r.date_reservation, 'YYYY-MM-DD') = :dateReservation)
        )
    ORDER BY r.date_reservation DESC
    """ , nativeQuery = true)
    public List<RESERVATION> researchForOne(
        @Param("email") String email ,
        @Param("depart") String depart , 
        @Param("arrivee") String arrivee , 
        @Param("dateReservation")  String dateReservation

    ) ; 
    @Query(value = """
    SELECT r.* 
    FROM reservation r 
    JOIN client c ON c.id_client = r.client_id 
    JOIN voyage v ON v.id_voyage = r.voyage_id
    WHERE 
        r.status = 'ANNULEE'
        AND
        (:email IS NULL OR c.mail_client = :email)
        AND (
            (:depart IS NULL OR REPLACE(v.depart_voyage, ' ', '') = REPLACE(:depart, ' ', ''))
            OR
            (:arrivee IS NULL OR v.arrive_voyage LIKE '%' || :arrivee || '%')
            OR
            (:dateReservation IS NULL OR TO_CHAR(r.date_reservation, 'YYYY-MM-DD') = :dateReservation)
        )
    ORDER BY r.date_reservation DESC
    """ , nativeQuery = true)
    public List<RESERVATION> researchForOneAnnulee(
        @Param("email") String email ,
        @Param("depart") String depart , 
        @Param("arrivee") String arrivee , 
        @Param("dateReservation")  String dateReservation

    ) ; 
    @Query(value = """
    SELECT r.* 
    FROM reservation r 
    JOIN client c ON c.id_client = r.client_id 
    JOIN voyage v ON v.id_voyage = r.voyage_id
    WHERE 
        r.status = 'EN_ATTENTE_CONFIRMATION'
        AND
        (:email IS NULL OR c.mail_client = :email)
        AND (
            (:depart IS NULL OR REPLACE(v.depart_voyage, ' ', '') = REPLACE(:depart, ' ', ''))
            OR
            (:arrivee IS NULL OR v.arrive_voyage LIKE '%' || :arrivee || '%')
            OR
            (:dateReservation IS NULL OR TO_CHAR(r.date_reservation, 'YYYY-MM-DD') = :dateReservation)
        )
    ORDER BY r.date_reservation DESC
    """ , nativeQuery = true)
    public List<RESERVATION> researchForOneEnCours(
        @Param("email") String email ,
        @Param("depart") String depart , 
        @Param("arrivee") String arrivee , 
        @Param("dateReservation")  String dateReservation

    ) ; 

    @Query(value = """
    SELECT r.* 
    FROM reservation r 
    JOIN client c ON c.id_client = r.client_id 
    JOIN voyage v ON v.id_voyage = r.voyage_id
    WHERE 
         
         
            (:depart IS NULL OR REPLACE(v.depart_voyage, ' ', '') = REPLACE(:depart, ' ', ''))
            OR
            (:arrivee IS NULL OR v.arrive_voyage LIKE '%' || :arrivee || '%')
            OR
            (:dateReservation IS NULL OR TO_CHAR(r.date_reservation, 'YYYY-MM-DD') = :dateReservation)
        
    ORDER BY r.date_reservation DESC
    """ , nativeQuery = true)
    public List<RESERVATION> research(
         
        @Param("depart") String depart , 
        @Param("arrivee") String arrivee , 
        @Param("dateReservation")  String dateReservation

    ) ; 
    @Query(value = """
    SELECT r.* 
    FROM reservation r 
    JOIN client c ON c.id_client = r.client_id 
    JOIN voyage v ON v.id_voyage = r.voyage_id
    WHERE r.status = 'ANNULEE'
    AND (
        (:depart IS NULL OR REPLACE(v.depart_voyage, ' ', '') = REPLACE(:depart, ' ', ''))
        OR
        (:arrivee IS NULL OR v.arrive_voyage LIKE '%' || :arrivee || '%')
         OR
        (:dateReservation IS NULL OR TO_CHAR(r.date_reservation, 'YYYY-MM-DD') = :dateReservation)
    )
    ORDER BY r.date_reservation DESC
    """ , nativeQuery = true)
    public List<RESERVATION> researchAnnulee(
         
        @Param("depart") String depart , 
        @Param("arrivee") String arrivee , 
        @Param("dateReservation")  String dateReservation

    ) ; 
    @Query(value = """
    SELECT r.* 
    FROM reservation r 
    JOIN client c ON c.id_client = r.client_id 
    JOIN voyage v ON v.id_voyage = r.voyage_id
    WHERE r.status = 'EN_ATTENTE_CONFIRMATION'
    AND (
        (:depart IS NULL OR REPLACE(v.depart_voyage, ' ', '') = REPLACE(:depart, ' ', ''))
        OR
        (:arrivee IS NULL OR v.arrive_voyage LIKE '%' || :arrivee || '%')
        OR
        (:dateReservation IS NULL OR TO_CHAR(r.date_reservation, 'YYYY-MM-DD') = :dateReservation)
    )
    ORDER BY r.date_reservation DESC
    """ , nativeQuery = true)
    public List<RESERVATION> researchEncours(
         
        @Param("depart") String depart , 
        @Param("arrivee") String arrivee , 
        @Param("dateReservation")  String dateReservation

    ) ; 

    
    @Query(value = """
    SELECT 
    COUNT(r.id_reservation) / COUNT(DISTINCT r.client_id) AS moyenne_reservations_par_client
    FROM 
    reservation r;
    """ , nativeQuery = true)
    public Float averageParClient() ;
    //(TO_CHAR(r.date_reservation, 'YYYY-MM-DD')


  /*   @Query(value="""
        SELECT SUM(r.montant)
    """ , nativeQuery = true)
    public Float GainTotal()
 */

    @Query(value = """
        SELECT r.* FROM reservation r
        JOIN client c ON r.client_id = c.id_client
        WHERE c.mail_client = :email AND r.status = 'ANNULEE'

    """ , nativeQuery = true)
    public List<RESERVATION> reservationAnnuleeForOne(@Param("email") String email);

    @Query(value = """
    SELECT 
        CAST(SUM(montant) AS DOUBLE PRECISION) AS revenuTotal,
        CAST(AVG(montant) AS DOUBLE PRECISION) AS gainMoyen
    FROM reservation
    WHERE EXTRACT(MONTH FROM date_reservation) = :mois
    AND EXTRACT(YEAR FROM date_reservation) = EXTRACT(YEAR FROM CURRENT_DATE)""", 
    nativeQuery = true)
    Map<String , Object>  getDataDashBoard1(@Param("mois") Integer mois);


      @Query(value = """
        SELECT r.* FROM reservation r
        WHERE    r.status = 'ANNULEE'

    """ , nativeQuery = true)
    public List<RESERVATION> reservationAnnulee();

    


      @Query(value = """
        SELECT r.* FROM reservation r
        JOIN client c ON r.client_id = c.id_client
        WHERE c.mail_client = :email ;

    """ , nativeQuery = true)
    public List<RESERVATION> reservationEffectuerForOne(@Param("email") String email) ;

      @Query(value = """
        SELECT r.* FROM reservation r
        JOIN client c ON r.client_id = c.id_client
        WHERE c.mail_client = :email AND r.status = 'EN_ATTENTE_CONFIRMATION'

    """ , nativeQuery = true)
    public List<RESERVATION> reservationEnCoursForOne(@Param("email") String email) ; 

    @Query(value = """
        SELECT r.* FROM reservation r
        JOIN client c ON r.client_id = c.id_client
        WHERE r.status = 'EN_ATTENTE_CONFIRMATION'

    """ , nativeQuery = true)
    public List<RESERVATION> reservationEncours() ;



    @Query(value="""
    SELECT (SELECT  
                (AVG(r.montant)   /   (SELECT AVG(r2.montant) 
                       FROM reservation r2 
                       WHERE EXTRACT(YEAR FROM r2.date_reservation) = EXTRACT(YEAR FROM CURRENT_DATE)
                       AND EXTRACT(MONTH FROM r2.date_reservation) = EXTRACT(MONTH FROM CURRENT_DATE)
            ))   AS avgValue 
            FROM reservation r  
            WHERE EXTRACT(YEAR FROM r.date_reservation) = EXTRACT(YEAR FROM CURRENT_DATE) LIMIT 100)
    *  100 AS pourcentValue
    """ , nativeQuery = true)
    public  Map<String , Object> newEarnPourcentage() ; 




    @Query(value="""
        SELECT SUM(r.montant)  AS gainTotal
        FROM reservation r
        WHERE r.status  = 'PAYEE' ;
    
    """ ,nativeQuery = true )
    public  Map<String , Object> gainTotal() ; 


 

}


