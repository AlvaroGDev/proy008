package es.cic.curso25.proy008.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso25.proy008.model.Conductor;
import es.cic.curso25.proy008.model.Viaje;
import es.cic.curso25.proy008.service.ConductorService;
import es.cic.curso25.proy008.service.ViajeService;

import java.util.List;

@RestController
@RequestMapping("/conductor")
public class ConductorController {

    @Autowired
    private ConductorService conductorService;

    @Autowired
    private ViajeService viajeService;

    @GetMapping("/{id}")
    public Conductor get(@PathVariable(required = true) Long id) {
        if (conductorService.get(id) == null)
            throw new SecurityException("Error: me estás intentando buscar un id que no existe");

        return conductorService.get(id);
    }

    @GetMapping()
    public List<Conductor> getAll() {
        return conductorService.getAll();
    }

    @PostMapping
    public Conductor create(@RequestBody Conductor conductor) {

        if (conductor.getId() != null)
            throw new SecurityException();

        return conductorService.create(conductor);
    }

    @PostMapping("/generaviaje")
    public Viaje create(@RequestBody Viaje viaje){
        Viaje viajeCreado = viajeService.create(viaje);
        return viajeCreado;
    }

    @PutMapping
    public void update(@RequestBody Conductor conductor) {
        if (conductor.getId() == null)
            throw new SecurityException("Estás intentando modificar un registro que no existe");

        conductorService.update(conductor);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(required = true) Long id) {
        conductorService.delete(id);
    }

}
