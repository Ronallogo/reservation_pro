package tg.voyage_pro.reservation_pro.database;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tg.voyage_pro.reservation_pro.Model.AGENT;
import tg.voyage_pro.reservation_pro.Security.entities.User;



@Repository
public interface AgentRepository extends JpaRepository<AGENT , Long> {

    
    @Query(value = "SELECT * FROM AGENT a ORDER BY a.id_agent DESC" , nativeQuery = true)
    List<AGENT> findAllOrderByIdAgentDesc();

    @Query(value = "SELECT * FROM agent c_1 WHERE c_1.id_agent IN (" +
    "SELECT c_2.id_agent FROM agent c_2 WHERE (:sexe IS NULL OR c_2.sexe_agent = '%' || :sexe || '%') OR (:date_naissance IS NULL OR (TO_CHAR(c_2.date_naiss, 'YYYY-MM-DD') = '%' || :date_naissance || '%')) " +
    ") OR c_1.id_agent IN (" +
    "SELECT c_3.id_agent FROM agent c_3 WHERE (:telephone IS NULL OR c_3.tel_agent LIKE '%' || :telephone || '%') " +
    ") OR c_1.id_agent IN (" +
    "SELECT c_4.id_agent FROM agent c_4 WHERE (:mail IS NULL OR c_4.mail_agent LIKE '%' || :mail || '%') " +
    ") OR c_1.id_agent IN (" +
    "SELECT c_5.id_agent FROM agent c_5 WHERE (:nom IS NULL OR c_5.nom_agent LIKE '%' || :nom || '%') " +
    ") OR c_1.id_agent IN (" +
    "SELECT c_6.id_agent FROM agent c_6 WHERE (:nom IS NULL OR c_6.prenom_agent LIKE '%' || :nom || '%') " +
    ") " +
    "ORDER BY c_1.id_agent DESC", 
    nativeQuery = true)

    List<AGENT> searchAgent( 
        @Param("nom") String nom,
        @Param("mail") String mail ,
        @Param("date_naissance")  String date_naissance ,
        @Param("sexe") String sexe ,
        @Param("telephone") String telephone 
    
    );

    @Query(value = "SELECT * FROM agent a WHERE a.login = :login", nativeQuery = true)
    Optional<AGENT> findByLogin(@Param("login") String login);


    @Query(value = "SELECT * FROM agent WHERE mail_agent = :mail ; "  , nativeQuery = true)
    Optional<AGENT> findByMailAgent(@Param("mail") String mailAgent) ;


    @Query(value= "SELECT * FROM  agent WHERE id_user = :id ; " , nativeQuery = true)
    Optional<AGENT>   findByUserId(@Param("id") Long id);
}
