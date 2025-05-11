package tg.voyage_pro.reservation_pro.voyage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VoyageRepository extends JpaRepository<VOYAGE , Long> {

}
