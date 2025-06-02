package tg.voyage_pro.reservation_pro.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
 
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
import tg.voyage_pro.reservation_pro.security.user._User;
import lombok.experimental.SuperBuilder;
import java.util.Date;
import java.util.List;

 

@Entity
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(
    name = "agent",
    uniqueConstraints = @UniqueConstraint(
        name = "unique_email", 
        columnNames = {"mailAgent"}
    ))
public class AGENT extends _User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agent")
    private Long idAgent ;
    @Column(name = "nom_agent" , length = 75 , nullable = false)
    private String nomAgent ;
    @Column(name = "prenom_agent" , length = 75)
    private String prenomAgent ;
    @Column(name = "sexe_agent" , nullable = false , length = 1)
    private String sexeAgent ;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd/mm/yyyy" , timezone = "UTC")
    @Column(name = "date_naiss" , nullable = false)
    private Date dateNaiss ;
    @Column(name = "tel_agent" , nullable = false , length = 20)
    private String  telAgent ;
    @Column(name = "mail_agent" , nullable = false , length = 75)
    private String  mailAgent ;
   

    @OneToMany(mappedBy = "agent" , cascade = CascadeType.ALL)
    private List<PAIEMENT> paiementList ;
 



    
     

}
