package tg.voyage_pro.reservation_pro.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tg.voyage_pro.reservation_pro.security.token.TOKEN;
import tg.voyage_pro.reservation_pro.security.user._User;

 
import java.util.Date;
import java.util.List;

 

@Entity
@Table(
    name = "client" , 
    uniqueConstraints = @UniqueConstraint(
        name = "unique_email", 
        columnNames = {"mailClient"}
    ))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class CLIENT extends _User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long  idClient ;
    @Column(name = "nom_client", nullable = false , length = 100)
    private String nomClient ;
    @Column(name="prenom_client"  ,  nullable = false , length = 100)
    private String prenomClient ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy", timezone = "UTC")
    @Column(name = "date_naiss" , nullable = false)
    private Date dateNaiss ;
    @Column( name="mail_client" , nullable = false , length = 100)
    private String mailClient ;
    @Column(name="tel_client"  , nullable = false , length = 50)
    private String telClient ;
    @Column(name="sexe_client" , nullable = false)
    private String sexeClient ;
     

    @OneToMany(mappedBy = "client" , cascade = CascadeType.ALL)
    private List<RESERVATION> reservations ;

    @OneToMany(mappedBy = "client" , cascade = CascadeType.ALL)
    private List<TOKEN> tokenList ;

    







}
