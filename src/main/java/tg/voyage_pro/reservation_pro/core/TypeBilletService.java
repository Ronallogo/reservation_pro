package tg.voyage_pro.reservation_pro.core;

 
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Service;

import tg.voyage_pro.reservation_pro.Model.TYPE_BILLET;
import tg.voyage_pro.reservation_pro.database.TypeBilletRepository;
import tg.voyage_pro.reservation_pro.dto.TypeBilletDTO;
import tg.voyage_pro.reservation_pro.mapperImpl.TypeBilletImpl;

@Service
public class TypeBilletService {
    @Autowired
    private TypeBilletRepository repo ; 

    private TypeBilletImpl  mapper = new TypeBilletImpl() ; 



    public List<TypeBilletDTO> research(String keyword){
        return this.mapper.toListDto(this.repo.research(keyword));
    }

    public TYPE_BILLET create( TYPE_BILLET type){
        return this.repo.save(type);
    }


    public List<TypeBilletDTO> all(){
        return  this.mapper.toListDto( this.repo.findAllDesc())  ;
        
        
    }


    public boolean delete(Long idType){
        if(this.repo.existsById(idType)){
            this.repo.deleteById(idType);
            return true ; 
        }
        return false  ;
    }

    public   TypeBilletDTO update( Long idType ,    TypeBilletDTO type){

        TYPE_BILLET  t = this.repo.findById(idType).orElse(null);

        if(t == null){
            return null ;
        }
        var id  = t.getIdTypeBillet() ; 
        t =  this.mapper.toEntity(type) ; 
        t.setIdTypeBillet(id);

     

        return this.mapper.toDto(this.repo.save(t));
    }

    public TypeBilletDTO get(Long idType){
        return  this.mapper.toDto(this.repo.findById(idType).orElse(null));
    }
}
