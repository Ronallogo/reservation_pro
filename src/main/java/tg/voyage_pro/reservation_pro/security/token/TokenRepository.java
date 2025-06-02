package tg.voyage_pro.reservation_pro.security.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TOKEN , Long>{
    @Query(value = """
        select t from token t inner join  agent u\s
        on t.user.id = u.id_agent\s
        where u.id_agent = :id and (t.expired = false or t.revoked = false)
        """ , nativeQuery = true)
      List<TOKEN> findAllValidTokenByAgent(Long id);
 
  
      @Query(value = """
        select t from token t inner join  agent u\s
        on t.user.id = u.id_agent\s
        where u.id_agent = :id AND role = 'AGENT' 
        """ , nativeQuery = true)
      List<TOKEN> findAllTokenByAgent(Long id);
      
    @Query(value = """
        select t from token t inner join   client u\s
        on t.user.id = u.id_client\s
        where u.id_client = :id and (t.expired = false or t.revoked = false)
        """ , nativeQuery = true)
      List<TOKEN> findAllValidTokenByClient(Long id);
  
      @Query(value = """
        select t from token t inner join   client u\s
        on t.user.id = u.id_client\s
        where u.id_cient = :id  AND u.role = 'CLIENT' 
        """ , nativeQuery = true)
      List<TOKEN> findAllTokenByClient(Long id);
  
  
  
      Optional<TOKEN> findByToken(String token);
  
}
