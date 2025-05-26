package tg.voyage_pro.reservation_pro.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tg.voyage_pro.reservation_pro.Model.TYPE_BILLET;

@Repository
public interface TypeBilletRepository extends JpaRepository<TYPE_BILLET , Long>{


}
