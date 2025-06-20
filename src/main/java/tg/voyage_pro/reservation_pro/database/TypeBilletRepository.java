package tg.voyage_pro.reservation_pro.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tg.voyage_pro.reservation_pro.Model.TYPE_BILLET;

@Repository
public interface TypeBilletRepository extends JpaRepository<TYPE_BILLET , Long>{



    @Query(value = "SELECT * FROM type_billet ORDER BY id_type_billet DESC ;" , nativeQuery = true)
    List<TYPE_BILLET>findAllDesc();


    @Query(value="""
                SELECT * FROM type_billet
                WHERE libelle_type_billet LIKE  CONCAT('%' , :keyword , '%') ; 
            """ , nativeQuery = true)
    public List<TYPE_BILLET> research(@Param("keyword") String keyword) ; 


}
