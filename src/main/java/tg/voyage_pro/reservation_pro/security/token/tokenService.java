package tg.voyage_pro.reservation_pro.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class tokenService {

    private TOKEN token  ; 

    @Autowired
    private TokenRepository tokenRepository ; 

    public boolean checkValidity(String token){
        this.token = this.tokenRepository.findByToken(token).get();
        if(this.token.isExpired() || this.token.isRevoked()){
            return false ; 
        }
        return true ; 
    }

    @PostConstruct
    public void startBackgroundCheckToken(){
        Thread backgroundThread = new Thread(() -> {
            while (true) {
                if(this.token !=  null) {
                    this.token.setRevoked(true);
                    this.token.setExpired(true);
                    this.tokenRepository.save(this.token);
                }
                try {
                    Thread.sleep(10000*6*5);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        backgroundThread.setDaemon(true); // Laisser le thread se terminer quand le serveur s'arrête
        backgroundThread.start();
    }


}
