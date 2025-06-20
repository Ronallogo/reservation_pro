package tg.voyage_pro.reservation_pro.database;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tg.voyage_pro.reservation_pro.Model.VOYAGE;


@Repository
public interface VoyageRepository extends JpaRepository<VOYAGE , Long> {
    @Query(value = "SELECT v.* FROM VOYAGE v ORDER BY v.id_voyage DESC" , nativeQuery = true)
    List<VOYAGE> findAllOrderByIdVoyageDesc();
    @Query(value = "SELECT v.* FROM VOYAGE v ORDER BY v.date_voyage DESC" , nativeQuery = true)
    List<VOYAGE> findAllByOrderByDateVoyageDesc();



    @Query(value = """
        SELECT * FROM voyage WHERE nbr_place_disponible > 0 AND date_voyage > CURRENT_DATE
    """  , nativeQuery = true)
    List<VOYAGE> voyageDisponible();




    @Query(value="""
    SELECT v.* 
    FROM voyage v 
    WHERE nbr_place_disponible > 0
    AND date_voyage > CURRENT_DATE
    AND (
        (:depart IS NOT NULL AND  REPLACE(v.depart_voyage, ' ', '') =  REPLACE(:depart, ' ', ''))
        OR
        (:arrivee IS NOT NULL AND REPLACE(v.arrive_voyage, ' ', '') =  REPLACE(:arrivee, ' ', ''))
        OR
        (:dateVoyage IS NOT NULL AND TO_CHAR(v.date_voyage, 'YYYY-MM-DD') = :dateVoyage)
    )
            
    """ , nativeQuery =  true)
    List<VOYAGE>researchListForClient(
        @Param("depart")  String depart ,
        @Param("arrivee") String arrivee  , 
        @Param("dateVoyage")  String dateVoyage
    ) ; 

    @Query(value="""
        SELECT v.* 
        FROM voyage v 
        WHERE  
            (:depart IS NOT NULL AND  REPLACE(v.depart_voyage, ' ', '') =  REPLACE(:depart, ' ', ''))
            OR
            (:arrivee IS NOT NULL AND REPLACE(v.arrive_voyage, ' ', '') =  REPLACE(:arrivee, ' ', ''))
            OR
            (:dateVoyage IS NOT NULL AND TO_CHAR(v.date_voyage, 'YYYY-MM-DD') = :dateVoyage) ORDER BY v.id_voyage DESC
    """ , nativeQuery =  true)
    List<VOYAGE>researchListForAgent(
        @Param("depart")  String depart ,
        @Param("arrivee") String arrivee  , 
        @Param("dateVoyage")  String dateVoyage
    ) ; 
    @Query(value="""
        SELECT v.* 
        FROM voyage v 
        WHERE  
        (
            (:depart IS NOT NULL AND  REPLACE(v.depart_voyage, ' ', '') =  REPLACE(:depart, ' ', ''))
            OR
            (:arrivee IS NOT NULL AND REPLACE(v.arrive_voyage, ' ', '') =  REPLACE(:arrivee, ' ', ''))
            OR
            (:dateVoyage IS NOT NULL AND TO_CHAR(v.date_voyage, 'YYYY-MM-DD') = :dateVoyage)
        )

        ORDER BY v.id_voyage DESC
    """ , nativeQuery =  true)
    List<VOYAGE>researchAllListForAgent(
        @Param("depart")  String depart ,
        @Param("arrivee") String arrivee  , 
        @Param("dateVoyage")  String dateVoyage
    ) ; 






    @Query(value="""
        SELECT  * FROM  voyage v
        WHERE  v.date_voyage >= CURRENT_DATE - INTERVAL '30 days'
    """ , nativeQuery = true)
    List<VOYAGE> voyageRecent() ; 

    @Query(value=" SELECT COUNT(*) FROM voyage" , nativeQuery = true)
    public  Float nbrTotal() ; 



    @Query(value="""
       SELECT COUNT(DISTINCT v.id_voyage)
        FROM voyage v
        JOIN reservation r ON r.voyage_id = v.id_voyage
    """ , nativeQuery = true)
    public List<Integer> CountOneByOneVolReserver();

    @Query(value = """
        SELECT DISTINCT v.depart_voyage FROM voyage v ; 
    """ , nativeQuery = true)
    public  List<String> Alldepart() ; 


    @Query(value = """
        SELECT DISTINCT v.arrive_voyage FROM voyage v ; 
    """ , nativeQuery = true)
    public  List<String> AllArrivee() ; 

      @Query(value = """
        SELECT DISTINCT v.depart_voyage FROM voyage v  
        WHERE v.nbr_place_disponible > 0
        AND v.date_voyage > CURRENT_DATE; 
    """ , nativeQuery = true)
    public List<String> AllDepartDisponible() ;



       @Query(value = """
        SELECT DISTINCT v.arrive_voyage FROM voyage v 
        WHERE v.nbr_place_disponible > 0
        AND v.date_voyage > CURRENT_DATE; 
    """ , nativeQuery = true)
    public List<String> AllArriveeDisponible() ;

    @Query(value=  """
            SELECT 
             v.arrive_voyage AS destination,
             COUNT(r.id_reservation) AS nombre_reservations
            FROM voyage v
            INNER JOIN reservation r ON v.id_voyage = r.voyage_id
            WHERE r.status != 'ANNULEE'   
            GROUP BY v.arrive_voyage
            ORDER BY nombre_reservations DESC
            LIMIT 3
            """ , nativeQuery = true)
    public List<Map<String , Object>> voyageTop3() ; 





}



