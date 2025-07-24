package es.cic.curso25.proy008.controller;

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

import java.util.List;
import java.util.Optional;

import es.cic.curso25.proy008.model.Viaje;
import es.cic.curso25.proy008.service.ViajeService;

@RestController
@RequestMapping("/viaje")
public class ViajeController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ViajeController.class);

    @Autowired
    private ViajeService viajeService;

    @GetMapping
    public List<Viaje> get(){
            LOGGER.info("Leo todos los viajes");

            return viajeService.get();
    }

    @GetMapping("/{id}")
    public Optional<Viaje> get(@PathVariable Long id){
        LOGGER.info("Leo el viaje con id: " + id);

        Optional<Viaje> viaje = viajeService.get(id);

        return viaje;
    }


    @PostMapping
    public Viaje create(@RequestBody Viaje viaje) {

        if (viaje.getId() != null)
            throw new SecurityException("No me puedes pasar un id");

        LOGGER.info("Creo un viaje");
        viaje = viajeService.create(viaje);

        return viaje;
    }

    @PutMapping
    public void update(@RequestBody Viaje viaje){
        LOGGER.info("Actualizo un viaje");

        if(viaje.getId() == null)
        throw new SecurityException("Me estas intentando crear un viaje a través de una modificación");
        // Si el ID es nulo, lo que está intentando es crearlo en vez de actualizarlo, por lo que lanzamos excepción

        viajeService.update(viaje);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        LOGGER.info("Borro un viaje");

        viajeService.delete(id);
        //No comprobamos nada porque si existe, lo borra, y si no existe, lo ignora.
    }



}