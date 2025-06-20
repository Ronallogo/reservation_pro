package tg.voyage_pro.reservation_pro.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.Tuple;
import tg.voyage_pro.reservation_pro.Model.PAIEMENT;


@Repository
public interface PaiementRepository extends JpaRepository<PAIEMENT , String> {


    @Query(value="""

    SELECT p.* 
    FROM paiement p 
    JOIN reservation r  ON r.id_reservation  = p.reservation_id 
    JOIN client c ON c.id_client = r.client_id 
    JOIN voyage v ON v.id_voyage = r.voyage_id
    WHERE 
        (:email IS NULL OR c.mail_client = :email)
        AND (
            (:depart IS NULL OR REPLACE(v.depart_voyage, ' ', '') = REPLACE(:depart, ' ', ''))
            OR
            (:arrivee IS NULL OR v.arrive_voyage LIKE '%' || :arrivee || '%')
            OR
           (:dateVoyage IS NULL OR TO_CHAR(v.date_voyage, 'YYYY-MM-DD') = :dateVoyage)
        )
    ORDER BY r.date_reservation DESC
    """
    , nativeQuery = true)
     

    public List<PAIEMENT> researchForOne(
        @Param("depart") String depart , 
        @Param("arrivee") String arrivee , 
        @Param("dateVoyage") String dateVoyage , 
        @Param("email") String email
        
    ) ; 


    @Query(value="""

    SELECT p.* 
    FROM paiement p 
    JOIN reservation r  ON r.id_reservation  = p.reservation_id 
    JOIN client c ON c.id_client = r.client_id 
    JOIN voyage v ON v.id_voyage = r.voyage_id
    WHERE 
         
         
            (:depart IS NULL OR REPLACE(v.depart_voyage, ' ', '') = REPLACE(:depart, ' ', ''))
            OR
            (:arrivee IS NULL OR v.arrive_voyage LIKE '%' || :arrivee || '%')
            OR
            (:dateVoyage IS NULL OR TO_CHAR(v.date_voyage, 'YYYY-MM-DD') = :dateVoyage)
        
    ORDER BY r.date_reservation DESC
    """
    , nativeQuery = true)
     

    public List<PAIEMENT> research(
        @Param("depart") String depart , 
        @Param("arrivee") String arrivee , 
        @Param("dateVoyage") String dateVoyage 
     
        
    ) ; 


    @Query(value = """
                SELECT p.* 
                FROM paiement p 
                JOIN reservation r  ON r.id_reservation  = p.reservation_id 
                JOIN client c ON c.id_client = r.client_id 
                WHERE c.mail_client = :email
            """ , nativeQuery = true)
    public List<PAIEMENT> getAllForOne(@Param("email") String  email);


        @Query(value ="""
        SELECT COUNT(p.code_paiement) * 100 AS nbr
        FROM paiement p 
        WHERE p.date_paiement >= CURRENT_DATE - INTERVAL '30 days'
    """ , nativeQuery =  true)
    public  Tuple  newPaiements();  

 


}
