package tg.voyage_pro.reservation_pro.Security.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
 

@Getter
@RequiredArgsConstructor
public enum Permission {
    AGENT_READ("agent:read")  ,
    AGENT_UPDATE("agent:update")  ,
    AGENT_DELETE("agent:delete")  ,
    AGENT_CREATE("agent:create") ,
    CLIENT_CREATE("client:create"),
    CLIENT_UPDATE("client:update") ,
    CLIENT_READ("client:read"),
    CLIENT_DELETE("client:delete") ,
    ADMIN_CREATE("admin:create") ,
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete")

    ;

    private final  String permission  ;
}