package es.cic.curso25.proy008.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso25.proy008.model.Coche;
import es.cic.curso25.proy008.service.CocheService;

@RestController
@RequestMapping("/coche")
public class CocheController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CocheController.class);

    @Autowired
    CocheService cocheService;

    @GetMapping("/{id}")
    public Optional<Coche> get(@PathVariable Long id) {
        if (cocheService.get(id) == null){
            throw new SecurityException("Error: me estás intentando buscar un id que no existe");
        }
        LOGGER.info("GET '/coche/id' obtener coche por id");
        Optional<Coche> coche = cocheService.get(id);
        return coche;
    }

    @GetMapping("")
    public List<Coche> getAll() {
        LOGGER.info("GET '/coche' obtener todos los coches");
        List<Coche> coches = cocheService.getAll();
        return coches;

    }

    @PostMapping
    public Long create(@RequestBody Coche coche) {
        LOGGER.info("POST '/coche' guardar coche en la BBDD");
        cocheService.create(coche);
        return coche.getId();
    }

    @PutMapping
    public void update(@RequestBody Coche coche) {
        if (coche.getId() == null){
            throw new SecurityException("Estás intentando modificar un registro que no existe");
        }
        LOGGER.info("PUT '/coche' actualizar coche en BBDD");
        cocheService.update(coche);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        LOGGER.info("DELETE '/coche/id' eliminar coche por id");
        cocheService.delete(id);
    }
}
