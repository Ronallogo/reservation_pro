package tg.voyage_pro.reservation_pro.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "paiement")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PAIEMENT implements Serializable {

    @Id
    @Column(name = "code_paiement" , nullable = false , length = 100)
    private  String codePaiement ;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id"  , nullable = false)
    private RESERVATION reservation ;
    @ManyToOne
    @JoinColumn(name = "agent_id" , nullable = false)
    private AGENT agent ;

    @Column(name = "date_paiement" , nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING  , pattern = "yyyy-MM-dd" , timezone = "UTC")
    private Date datePaiement ;

    


}
