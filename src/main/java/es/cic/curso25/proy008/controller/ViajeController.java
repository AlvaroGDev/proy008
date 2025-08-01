package es.cic.curso25.proy008.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.CREATED) 
    // Lo que le decimos: Si este método acaba bien, me devuelves un created (codigo 201), si no, devuelve 200, que es un código que dice que está bien, pero podemos querer algo específico
    public Viaje create(@RequestBody Viaje viaje) {

        if (viaje.getId() != null)
            throw new SecurityException("No me puedes pasar un id para generar un registro");

        LOGGER.info("Creo un viaje");
        viaje = viajeService.create(viaje);

        return viaje;
    }

    @PutMapping
    public Viaje update(@RequestBody Viaje viaje){
        LOGGER.info("Actualizo un viaje");

        if(viaje.getId() == null)
        throw new SecurityException("O te ha faltado el ID, o has intentado crear un registro mediante modificación.");
        // Si el ID es nulo, lo que está intentando es crearlo en vez de actualizarlo, por lo que lanzamos excepción

        return viajeService.update(viaje);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        LOGGER.info("Borro un viaje");

        viajeService.delete(id);
        //No comprobamos nada porque si existe, lo borra, y si no existe, lo ignora.
    }



}