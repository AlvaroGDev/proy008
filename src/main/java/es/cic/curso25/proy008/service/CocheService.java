package es.cic.curso25.proy008.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso25.proy008.model.Coche;
import es.cic.curso25.proy008.repository.CocheRepository;

@Service
public class CocheService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CocheService.class);

    @Autowired
    CocheRepository cocheRepository;

    public Long create(Coche coche){
        cocheRepository.save(coche);
        LOGGER.info("Coche creado con id " + coche.getId());
        return coche.getId();
    }

    public Optional<Coche> get(Long id){
        Optional<Coche> coche = cocheRepository.findById(id);
        LOGGER.info("Coche con id " + coche.get().getId() + " listado");
        return coche;
    }

    public List<Coche> getAll(){
        List<Coche> coches = cocheRepository.findAll();
        LOGGER.info("Todos los coches listados");
        return coches;
    }

    public void update(Coche coche) {
        cocheRepository.save(coche);
        LOGGER.info("Coche con id " + coche.getId() + " actualizado");
    }

    public void delete(Long id) {
        cocheRepository.deleteById(id);
        LOGGER.info("Coche con id " + id + " eliminado");
    }

}
