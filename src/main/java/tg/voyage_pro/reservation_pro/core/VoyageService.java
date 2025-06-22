package tg.voyage_pro.reservation_pro.core;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
 
import tg.voyage_pro.reservation_pro.Model.VOYAGE;
import tg.voyage_pro.reservation_pro.database.VoyageRepository;
import tg.voyage_pro.reservation_pro.dto.VoyageDTO;
import tg.voyage_pro.reservation_pro.exceptions.VoyageNotFoundException;
import tg.voyage_pro.reservation_pro.mapperImpl.VoyageMapperImpl;


@Service
@Transactional
 
public class VoyageService {

    @Autowired
    private VoyageRepository vr ;

     private VoyageMapperImpl mapper = new VoyageMapperImpl() ;


 

   


    public VoyageDTO create(VoyageDTO voyage){
        return this.mapper.toDto(
                this.vr.save(this.mapper.toEntity(voyage)));
    }



    public List<VoyageDTO> getAll(){
        return this.mapper.toDtos(this.vr.findAllByOrderByDateVoyageDesc()) ;
    }
        


    public VoyageDTO update(Long idVoyage ,   VoyageDTO voyage){

        if(!this.vr.existsById(idVoyage)){
            throw new VoyageNotFoundException("Aucun voyage n 'a ce numéro");
        }

        VOYAGE v = this.vr.findById(idVoyage).get() ;
         
        v.setDateVoyage(voyage.getDateVoyage());
        v.setDepartVoyage(voyage.getDepartVoyage());
        v.setArriveVoyage(voyage.getArriveVoyage());
        v.setNbrPlaceDisponible(v.getNbrPlaceDisponible());
        v.setIdVoyage(idVoyage);
        return this.mapper.toDto(this.vr.save(v)) ; 
        

        
 
    }

    public List<VoyageDTO> recent(){
        return this.mapper.toDtos( this.vr.voyageRecent());
    }

    public boolean delete(Long idVoyage){
        if(this.vr.existsById(idVoyage)){
            this.vr.deleteById(idVoyage);
            return true ; 
        }
        return false ; 
    }


    public  VOYAGE get(Long idVoyage){
        return this.vr.findById(idVoyage).orElse(null);

    }



    public List<VoyageDTO> voyageDisponible(){
        return  this.mapper.toDtos(this.vr.voyageDisponible())  ;

    }

    public List<VoyageDTO> researchForClient(Date dateVoyage , String departVoyage , String arriveVoyage){
        return this.mapper.toDtos(this.vr.researchListForClient(departVoyage, arriveVoyage, dateVoyage.toString())) ; 
    }


    public List<VoyageDTO> researchForAgent(Date dateVoyage , String departVoyage , String arriveVoyage){
        return this.mapper.toDtos(this.vr.researchListForAgent(departVoyage, arriveVoyage, dateVoyage.toString())) ; 
    }

    public Float recentVoyageAverage(){
        List<VOYAGE> voyage = this.vr.voyageRecent() ;

        Float nbrTotal = this.vr.nbrTotal() ; 
        return 100 - (( voyage.size() * 100) /  nbrTotal) ; 
        
        
    }

    public Integer volReserver(){
        return this.vr.CountOneByOneVolReserver().stream()
        .mapToInt(Integer::intValue)
        .sum();
    }

    public List<String> AllDepart(){
        return this.vr.Alldepart() ;
    }


    public List<String> AllArrive(){
        return this.vr.AllArrivee() ; 
    }


    public List<String>AllDepartDisponible(){
        return this.vr.AllDepartDisponible() ; 
    }

    public List<String>AllArriveeDisponible(){
        return this.vr.AllArriveeDisponible() ; 

    }

    public  String voyageTop3(){

     /*   List<String> listString = this.vr.voyageTop3();

        listString.forEach(x->{
            System.err.println(x);
        });*/
               
        return ""   ; 
    }
    
  

}
