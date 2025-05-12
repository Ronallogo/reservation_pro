package tg.voyage_pro.reservation_pro.core;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
 
import tg.voyage_pro.reservation_pro.Model.VOYAGE;
import tg.voyage_pro.reservation_pro.database.VoyageRepository;
import tg.voyage_pro.reservation_pro.dto.VoyageDTO;
import tg.voyage_pro.reservation_pro.exceptions.VoyageNotFoundException;
 

@Service
@Transactional
 
public class VoyageService {

    @Autowired
    private VoyageRepository vr ; 

 

   


    public  VOYAGE create(VoyageDTO voyage){
        return this.vr.save(
                VOYAGE.builder()
                .libelleVoyage( voyage.getLibelleVoyage())
                .dateVoyage(voyage.getDateVoyage())
        
            .build());

    

        
        
    }



    public List<VoyageDTO> getAll(){
        return this.vr.findAll().stream().map(x->
            VoyageDTO.builder()
            .idVoyage(x.getIdVoyage())
            .dateVoyage(x.getDateVoyage())
            .libelleVoyage( x.getLibelleVoyage())
            .build()
        
        ).collect(Collectors.toList());
    }
        


    public VoyageDTO update(VoyageDTO voyage){

        if(!this.vr.existsById(voyage.getIdVoyage())){
            throw new VoyageNotFoundException("Aucun voyage n 'a ce numéro");
        }

        VOYAGE v = this.vr.findById(voyage.getIdVoyage()).get() ;
         
        BeanUtils.copyProperties(voyage, v);
        this.vr.save(v) ;

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
        
        if(!this.vr.existsById(idVoyage)){
            throw new VoyageNotFoundException("Aucun voyage n 'a ce numéro");
        }

        Optional<VoyageDTO> voyage =  this.vr.findById(idVoyage).map(x->
            VoyageDTO.builder()
            .idVoyage(x.getIdVoyage())
            .dateVoyage(x.getDateVoyage())
            .libelleVoyage(x.getLibelleVoyage())
            .build()
        );

        return voyage.get() ; 

    }
}
