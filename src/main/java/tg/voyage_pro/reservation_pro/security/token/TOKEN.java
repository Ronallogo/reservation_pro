package tg.voyage_pro.reservation_pro.security.token;
 

import java.sql.Date;

 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tg.voyage_pro.reservation_pro.Model.AGENT;
import tg.voyage_pro.reservation_pro.Model.CLIENT;
import tg.voyage_pro.reservation_pro.security.user.Roles;
import tg.voyage_pro.reservation_pro.security.user.TypeToken;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor  
@Entity
@Table(name = "token")
public class TOKEN{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String token ;
    private  TypeToken tokenType  = TypeToken.BEARER;
    @Column(name = "expiration", nullable = false)
    private Date expiration ;
    @Column(name = "revoked", nullable = false)
    private boolean revoked ;
    @Column(name = "expired", nullable = false)
    private boolean expired ;
 

    @Column(name = "user_id", nullable = false)
    private Long userId; // Référence soit Agent, soit Client

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Roles role; // CLIENT ou AGENT

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private AGENT agent;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private CLIENT client;


}
