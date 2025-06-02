package tg.voyage_pro.reservation_pro.mappers;


 
import tg.voyage_pro.reservation_pro.Model.AGENT;

import tg.voyage_pro.reservation_pro.dto.AgentDTO;
 

import java.util.List;

 
public interface AgentMapper {
    AGENT toEntity( AgentDTO agentDto);
    AgentDTO toDto(AGENT agent);
    List<AgentDTO> toListDto(List<AGENT> list) ;
    AgentDTO toCustomDto(AGENT a)   ;

    AGENT toEntityForRegistration(AgentDTO dto) ; 
    

    List<AgentDTO> toCustomDtos(List<AGENT> list) ; 
}
