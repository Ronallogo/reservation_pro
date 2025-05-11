package tg.voyage_pro.reservation_pro.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<CLIENT , Long> {
}
