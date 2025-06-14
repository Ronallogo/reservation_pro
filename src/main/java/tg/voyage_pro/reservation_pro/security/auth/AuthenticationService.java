package tg.voyage_pro.reservation_pro.Security.auth;

 
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.Security.Config.JwtService;
import tg.voyage_pro.reservation_pro.Security.Token.Token;
import tg.voyage_pro.reservation_pro.Security.Token.TokenRepository;
import tg.voyage_pro.reservation_pro.Security.Token.TypeToken;
import tg.voyage_pro.reservation_pro.Security.entities.User;
import tg.voyage_pro.reservation_pro.Security.entities.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository ;
    private  final TokenRepository  tokenRepository;
    private final PasswordEncoder passwordEncoder ;
    private  final JwtService jwtService ;
    private final AuthenticationManager authenticationManager ;
   


    public AuthenticationResponse register(RegisterRequest request){
        var user = User.builder()
                
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        var saveUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(saveUser , jwtToken);
        return  AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(saveUser)
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
                .orElseThrow();

        var jwtToken = jwtService.generateRefreshToken(user);
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

}



