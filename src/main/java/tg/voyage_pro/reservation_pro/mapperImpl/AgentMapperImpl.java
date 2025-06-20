package tg.voyage_pro.reservation_pro.mapperImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tg.voyage_pro.reservation_pro.Model.AGENT;
import tg.voyage_pro.reservation_pro.dto.AgentDTO;
 
import tg.voyage_pro.reservation_pro.mappers.AgentMapper;
@RequiredArgsConstructor
@Component
public class AgentMapperImpl implements AgentMapper{


    private final PasswordEncoder passwordEncoder ;
    @Override
    public AGENT toEntity(AgentDTO dto) {
        return AGENT.builder()
        .nomAgent(dto.getNomAgent())
        .prenomAgent(dto.getPrenomAgent())
        .mailAgent(dto.getMailAgent())
        .telAgent(dto.getTelAgent())
        .dateNaiss(dto.getDateNaiss())
        .sexeAgent(dto.getSexeAgent())  
        .build();
    }

    


    @Override
    public AgentDTO toDto(AGENT agent) {
        return AgentDTO.builder()
        .idAgent(agent.getIdAgent())
        .nomAgent(agent.getNomAgent())
        .prenomAgent(agent.getPrenomAgent())
        .mailAgent(agent.getMailAgent())
        .telAgent(agent.getTelAgent())
        .dateNaiss(agent.getDateNaiss())
        .sexeAgent(agent.getSexeAgent())
      
        .build();
    }

    @Override
    public List<AgentDTO> toListDto(List<AGENT> list) {
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public AgentDTO toCustomDto(AGENT a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toCustomDto'");
    }

    @Override
    public List<AgentDTO> toCustomDtos(List<AGENT> list) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toCustomDtos'");
    }




    @Override
    public AGENT toEntityForRegistration(AgentDTO dto) {
        // TODO Auto-generated method stub
        return AGENT.builder()
        .idAgent(dto.getIdAgent())
        .nomAgent(dto.getNomAgent())
        .prenomAgent(dto.getPrenomAgent())
        .mailAgent(dto.getMailAgent())
        .telAgent(dto.getTelAgent())
        .dateNaiss(dto.getDateNaiss())
        .sexeAgent(dto.getSexeAgent())
         
    
        .build();
    }

    
}
