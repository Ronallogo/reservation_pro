package tg.voyage_pro.reservation_pro.Security.entities ; 


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import tg.voyage_pro.reservation_pro.Model.AGENT;
import tg.voyage_pro.reservation_pro.Model.CLIENT;
import tg.voyage_pro.reservation_pro.Model.RESERVATION;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "_user" , uniqueConstraints = @UniqueConstraint(
    name = "unique_username", 
    columnNames = {"username"}
))

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(unique = true, nullable = false)
    private String  username;
    private String password ;
    @Enumerated(EnumType.STRING)
    private Roles role ;

    
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<AGENT>  agents ;
    
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<CLIENT>   clients ;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return  username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
  }
}