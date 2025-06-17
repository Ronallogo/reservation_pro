package tg.voyage_pro.reservation_pro.Security.auth;

 
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.Model.ACTOR;
import tg.voyage_pro.reservation_pro.Model.*;
import tg.voyage_pro.reservation_pro.Security.Config.JwtService;
import tg.voyage_pro.reservation_pro.Security.Token.Token;
import tg.voyage_pro.reservation_pro.Security.Token.TokenRepository;
import tg.voyage_pro.reservation_pro.Security.Token.TypeToken;
import tg.voyage_pro.reservation_pro.Security.entities.User;
import tg.voyage_pro.reservation_pro.Security.entities.UserRepository;
import tg.voyage_pro.reservation_pro.database.AgentRepository;
import tg.voyage_pro.reservation_pro.database.ClientRepository;

 
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository ;
    private  final TokenRepository  tokenRepository;
    private final PasswordEncoder passwordEncoder ;
    private  final JwtService jwtService ;
    private final AuthenticationManager authenticationManager ;
    private final AgentRepository repoAgent ; 
    private final ClientRepository repoClient ; 
   


    public AuthenticationResponse register(RegisterRequest request  ){
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
 
        var saveUser = repository.save(user);
        Map<String , Object> extraClaims  =  setExtraClaims(saveUser); 

        var jwtToken = jwtService.generateToken(extraClaims , user) ; 
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(saveUser , jwtToken);
        return  AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(saveUser)
                .build();
    }



    
    public AuthenticationResponse register(RegisterRequest request , Object o){
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
 
        var saveUser = repository.save(user);
        Map<String , Object> extraClaims  =  setExtraClaims(saveUser , o); 

        var jwtToken = jwtService.generateToken(extraClaims , user) ; 
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(saveUser , jwtToken);
        return  AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(saveUser)
                .build();
    }


    
    public AuthenticationResponse update(RegisterRequest request  ){
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        var saveUser = this.repository.findByUsername(request.getUsername())
        .orElseThrow(()-> new RuntimeException("user not found"));
        
        Map<String , Object> extraClaims  =  setExtraClaims(saveUser);

        var jwtToken = jwtService.generateToken(extraClaims , user) ; 
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(saveUser , jwtToken);
        return  AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                
                .build();
    }







    private void revokeAllUserToken(User user){
        var validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validUserToken.isEmpty()){
            return;
        }
        validUserToken.forEach(token -> {
                token.setExpired(true) ;
                token.setRevoked(true);

        });
        tokenRepository.saveAll(validUserToken);
    }


    private void saveUserToken(User user , String jwtToken){
           var token  = Token.builder()
                   .user(user)
                   .token(jwtToken)
                   .tokenType(TypeToken.BEARER)
                   .expired(false)
                   .revoked(false)
                   .build() ;

           tokenRepository.save(token);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername() ,
                request.getPassword()
            )
        ) ;
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow(()-> new RuntimeException("User not found"));
         
        Map<String , Object> extraClaims  =  setExtraClaims(user);
       
        var jwtToken = jwtService.generateToken(extraClaims ,  user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserToken(user);
        saveUserToken(user , jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build() ;


    }

/*     public void refreshToken(
        HttpServletRequest request ,
        HttpServletResponse response
    ) throws IOException {
        final  String authHeader  =  request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken ;
        final  String userEmail ;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null){
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();

            if(jwtService.isTokenValid(refreshToken , user)){
                var accessToken = jwtService.generateToken(user);
                revokeAllUserToken(user);
                saveUserToken(user , accessToken);

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream() , authResponse);
            }
        }


    }*/


    public List<User> getAllUser(){
        return repository.findAll() ;
    }

   /*  public AuthenticationResponse editUserWithPassword(Long id ,   RegisterRequest userData){

        Employee e = employeeRepository.findEmployeeByIdUser(id)
                .orElseThrow(()-> new EmployeeNotFoundException("employee not found"));

        e.setEmail(userData.getEmail());
        this.employeeRepository.save(e);

        var user = User.builder()

                .firstname(userData.getFirstname())
                .lastname(userData.getLastname())
                .email(userData.getEmail())
                .password(passwordEncoder.encode(userData.getPassword()))
                .role(userData.getRole())
                .build();
        user.setId(id);
        var saveUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(saveUser , jwtToken);
        return  AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }*/


 /*    public UserResponse findUser(String email){
       User user =   this.repository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("user not found"));

       return UserResponse.builder()
               .id(user.getId())
               .firstname(user.getFirstname())
               .lastname(user.getLastname())
               .email(user.getEmail())
               .role(user.getRole().name()).build() ;
    }*/


   /*  public AuthenticationResponse  editUserWithoutPassword(Long id ,   UserResponse userData){

        var userFetched = this.repository.findById(userData.getId())
                .orElseThrow(()-> new UsernameNotFoundException("user not found!!!"));

        var user = User.builder()

                .firstname(userData.getFirstname())
                .lastname(userData.getLastname())
                .email(userData.getEmail())
                .password(passwordEncoder.encode(userFetched.getPassword()))
                .role(userFetched.getRole())
                .build();
        user.setId(id);
        var saveUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(saveUser , jwtToken);
        return  AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }*/


    public boolean delete(Long id){
        if(this.repository.existsById(id)){
            List<Token> listToken =   this.tokenRepository.findAllTokenByUser(id);
            this.tokenRepository.deleteAll(listToken);
            this.repository.deleteById(id);
            return  true;
        }
        else{
            throw  new RuntimeException("user not found ");
        }

    }

    public boolean checkValidityToken(AuthenticationResponse authenticationResponse){
            var token =   this.tokenRepository.findByToken(authenticationResponse.getAccessToken());
            return  token.isPresent() && !token.get().isRevoked() && !token.get().isExpired();
    }

    public Map<String , Object> setExtraClaims(User user){

        Map<String , Object> extraClaims = new HashMap<>() ; 
       

        if(
            user.getRole().name() == "AGENT"||
            user.getRole().name() == "ADMIN"
        ) {
            
            var entity =  this.repoAgent.findByUserId(user.getId())
            .orElseThrow(()-> new RuntimeException("User not found for agent registration")) ; 
         
            extraClaims.put("firstname" ,  entity.getPrenomAgent()) ; 
            extraClaims.put("lastname" , entity.getNomAgent()) ; 
            extraClaims.put("sexe" ,  entity.getSexeAgent()) ; 
            extraClaims.put("phone" ,  entity.getTelAgent()) ; 
            extraClaims.put("email" ,  entity.getMailAgent()) ;
    
        }else if( user.getRole().name() == "CLIENT"){
            
            CLIENT entity = this.repoClient.findByUserId(user.getId()).get() ; 
             
            
            extraClaims.put("firstname" ,  entity.getPrenomClient()) ; 
            extraClaims.put("lastname" , entity.getNomClient()) ; 
            extraClaims.put("sexe" ,  entity.getSexeClient()) ; 
            extraClaims.put("phone" ,  entity.getTelClient()) ; 
            extraClaims.put("email" ,  entity.getMailClient()) ;
    
        }
        return extraClaims ; 
        
         
    }



    public Map<String , Object> setExtraClaims(User user ,  Object actor){

        Map<String , Object> extraClaims = new HashMap<>() ; 
       

        if(
            user.getRole().name() == "AGENT"||
            user.getRole().name() == "ADMIN"
        ) {
            
            AGENT agent = (AGENT)actor ; 
            agent.setUser(user);
            var  entity =  this.repoAgent.save( agent);
             
            extraClaims.put("firstname" ,  entity.getPrenomAgent()) ; 
            extraClaims.put("lastname" , entity.getNomAgent()) ; 
            extraClaims.put("sexe" ,  entity.getSexeAgent()) ; 
            extraClaims.put("phone" ,  entity.getTelAgent()) ; 
            extraClaims.put("email" ,  entity.getMailAgent()) ;
    
        }else if( user.getRole().name() == "CLIENT"){
            
            CLIENT  client = (CLIENT)actor ; 
            client.setUser(user);
            var  entity =  this.repoClient.save(client);
             
            System.out.print(entity.toString());
            extraClaims.put("firstname" ,  entity.getPrenomClient()) ; 
            extraClaims.put("lastname" , entity.getNomClient()) ; 
            extraClaims.put("sexe" ,  entity.getSexeClient()) ; 
            extraClaims.put("phone" ,  entity.getTelClient()) ; 
            extraClaims.put("email" ,  entity.getMailClient()) ;
    
        }
        return extraClaims ; 
        
         
    }

}



