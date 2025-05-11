package tg.voyage_pro.reservation_pro.voyage;

import java.util.List;
 
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
 
import tg.voyage_pro.reservation_pro.exceptions.VoyageNotFoundException;

@Service
@Transactional
public class VoyageService {

    @Autowired
    private VoyageRepository vr ; 


    public VoyageDTO create(VoyageDTO voyage){

       var v =  this.vr.save( VOYAGE.builder()
            .dateVoyage( voyage.getDateVoyage())
            .libelleVoyage( voyage.getLibelleVoyage()) 
        .build());

        voyage.setIdVoyage( v.getIdVoyage());

        return voyage  ; 
    }

    public List<VoyageDTO> getAll(){
        return this.vr.findAll().stream().map( x->
                VoyageDTO.builder()
                .idVoyage( x.getIdVoyage())
                .dateVoyage(x.getDateVoyage())
                .libelleVoyage(x.getLibelleVoyage()) 
                .build()
        ).collect(Collectors.toList());
    }


    public VoyageDTO update(VoyageDTO voyage){

        VOYAGE v = this.vr.findById(voyage.getIdVoyage()).orElse(null) ; 

        if(v == null){
                throw new VoyageNotFoundException("Voyage not found");
        }

        v.setDateVoyage(voyage.getDateVoyage());
        v.setLibelleVoyage(voyage.getLibelleVoyage());
        this.vr.save(v);
        return voyage ; 
    }

    public boolean delete(Long idVoyage){
        if(this.vr.existsById(idVoyage)){
            this.vr.deleteById(idVoyage);
            return true ; 
        }
        return false ; 
    }


    public VoyageDTO get(Long idVoyage){
        VOYAGE voyage = this.vr.findById(idVoyage).orElse(null) ; 

        return  VoyageDTO.builder()
            .idVoyage(voyage.getIdVoyage())
            .dateVoyage( voyage.getDateVoyage()) 
            .libelleVoyage( voyage.getLibelleVoyage())
            .build() ; 

    }
}
