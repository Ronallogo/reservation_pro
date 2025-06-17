package tg.voyage_pro.reservation_pro.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
import lombok.experimental.SuperBuilder;
import tg.voyage_pro.reservation_pro.Security.entities.User;

import java.util.Date;
import java.util.List;

 

@Entity
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(
    name = "agent",
    uniqueConstraints = @UniqueConstraint(
        name = "unique_email", 
        columnNames = {"mailAgent"}
    ))
public class AGENT   extends ACTOR{

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

    @JoinColumn(name="id_user"   , nullable = false)
    @ManyToOne
    private User user ; 
   

    @OneToMany(mappedBy = "agent" , cascade = CascadeType.ALL)
    private List<PAIEMENT> paiementList ;
    



    
     

}
