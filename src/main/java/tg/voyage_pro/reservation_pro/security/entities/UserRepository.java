package tg.voyage_pro.reservation_pro.Security.entities ; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String  username);
}
