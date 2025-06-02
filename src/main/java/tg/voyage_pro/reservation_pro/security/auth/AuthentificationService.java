package tg.voyage_pro.reservation_pro.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.database.AgentRepository;
import tg.voyage_pro.reservation_pro.database.ClientRepository;

@Service
@RequiredArgsConstructor
public class AuthentificationService {
    private final AgentRepository agentRepository ; 
    private final ClientRepository clientRepository ; 
    private final PasswordEncoder passwordEncoder ; 
   // private final JwtService jwtService ; 
    private final AuthenticationManager authenticationManager ; 

}
