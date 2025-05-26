package tg.voyage_pro.reservation_pro.core;

 
import java.util.List;
import java.util.stream.Collectors;

 
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Service;

import tg.voyage_pro.reservation_pro.Model.TYPE_BILLET;
import tg.voyage_pro.reservation_pro.database.TypeBilletRepository;
import tg.voyage_pro.reservation_pro.dto.TypeBilletDTO;

@Service
public class TypeBilletService {
    @Autowired
    private TypeBilletRepository repo ; 

    public TYPE_BILLET create( TYPE_BILLET type){
        return this.repo.save(type);
    }


    public List<TYPE_BILLET> all(){
        return  this.repo.findAll() ;
        
        
    }


    public boolean delete(Long idType){
        if(this.repo.existsById(idType)){
            this.repo.deleteById(idType);
            return true ; 
        }
        return false  ;
    }

    public  TYPE_BILLET update( Long idType ,   TYPE_BILLET type){

        TYPE_BILLET  t = this.repo.findById(idType).orElse(null);

        if(t == null){
            return null ;
        }

        BeanUtils.copyProperties(type, t);
        t.setIdTypeBillet(idType);

        this.repo.save(t);

        return type ; 
    }

    public TypeBilletDTO get(Long idType){
        return (TypeBilletDTO) this.repo.findById(idType).stream().map( x->
            TypeBilletDTO.builder()
            .idTypeBillet(x.getIdTypeBillet())
            .libelleTypeBillet( x.getLibelleTypeBillet())
            .build()
        );
    }
}
