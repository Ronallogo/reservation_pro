package tg.voyage_pro.reservation_pro.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tg.voyage_pro.reservation_pro.exceptions.ClientNotFoundException;
 

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ClientService {
    @Autowired
    private   ClientRepository clientRepository ;


    public ClientDTO create(ClientDTO client){

        this.clientRepository.save(
                CLIENT.builder()
                        .nomClient(client.getNomClient())
                        .prenomClient(client.getPrenomClient())
                        .dateNaiss(client.getDateNaiss())
                        .telClient(client.getSexeClient())
                        .mailClient(client.getMailClient())
                        .telClient(client.getTelClient())
                        .sexeClient(client.getSexeClient())
                        .login(client.getLogin())
                        .password(client.getPassword())
                        .build()
        ) ;
        return  client ;
    }


    public List<ClientDTO> getAllClient(){
        return this.clientRepository.findAll().stream().map(x->
                ClientDTO.builder()
                        .idClient(x.getIdClient())
                        .nomClient(x.getNomClient())
                        .prenomClient(x.getPrenomClient())
                        .dateNaiss(x.getDateNaiss())
                        .telClient(x.getSexeClient())
                        .mailClient(x.getMailClient())
                        .sexeClient(x.getSexeClient())
                        .telClient(x.getTelClient()).build()
                ).collect(Collectors.toList()) ;
    }


    public ClientDTO getClient(Long idClient){
        var client = this.clientRepository.findById(idClient).orElse(null);
        if(client == null){
            throw new ClientNotFoundException("Client not found");
        }

        return ClientDTO.builder()
                .idClient(client.getIdClient())
                .nomClient(client.getNomClient())
                .prenomClient(client.getPrenomClient())
                .dateNaiss(client.getDateNaiss())
                .telClient(client.getSexeClient())
                .mailClient(client.getMailClient())
                .telClient(client.getTelClient()
                ).sexeClient(client.getSexeClient()).build() ;

    }

    public ClientDTO update(ClientDTO client){
        var c = this.clientRepository.findById(client.getIdClient()).orElse(null);
        if(c == null) {
            throw new ClientNotFoundException("Client not found");
        }
        c.setNomClient(client.getNomClient());
        c.setMailClient(client.getMailClient());
        c.setPrenomClient(client.getPrenomClient());
        c.setDateNaiss(client.getDateNaiss());
        c.setTelClient(client.getTelClient());
        c.setSexeClient(client.getSexeClient());
        
        this.clientRepository.save(c);
        return client ;


    }

    public boolean delete(Long idClient){
        if(this.clientRepository.existsById(idClient)){
            this.clientRepository.deleteById(idClient); ;
            return true ;
        }
        return false ;
    }






}
