package tg.voyage_pro.reservation_pro.Security.Token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tg.voyage_pro.reservation_pro.Security.entities.User;

import tg.voyage_pro.reservation_pro.Security.Token.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue
    public  Integer id ;

    @Column(unique = true)
    public String token ;


    @Enumerated(EnumType.STRING)
    public TypeToken tokenType = TypeToken.BEARER;

    public  boolean revoked ;

    public boolean expired ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;



}
