package tg.voyage_pro.reservation_pro.security.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static tg.voyage_pro.reservation_pro.security.user.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Roles {

    CLIENT(
            Set.of(
                    CLIENT_CREATE ,
                    CLIENT_DELETE ,
                    CLIENT_UPDATE   ,
                    CLIENT_READ
            )
    ) ,

    ADMIN(
            Set.of(
                    ADMIN_CREATE  ,
                    ADMIN_DELETE,
                    ADMIN_READ ,
                    ADMIN_UPDATE
            )
    ) ,



    AGENT(Set.of(
            AGENT_CREATE ,
            AGENT_DELETE ,
            AGENT_READ ,
            AGENT_UPDATE
    ))    ;




    private final Set<Permission> permissionSet ;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = new java.util.ArrayList<>(getPermissionSet()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return  authorities ;
    }



}
