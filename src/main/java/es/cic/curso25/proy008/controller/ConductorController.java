package es.cic.curso25.proy008.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso25.proy008.model.Conductor;
import es.cic.curso25.proy008.service.ConductorService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/conductor")
public class ConductorController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConductorController.class);

    @Autowired
    private ConductorService conductorService;

    @GetMapping("/{id}")
    public Conductor get(@PathVariable Long id){
        return conductorService.get(id);
    }

    @GetMapping()
    public List<Conductor> getAll(){
        return conductorService.getAll();
    }


    @PostMapping
    public Conductor create(@RequestBody Conductor conductor) {
      
           if (conductor.getId() != null)
                throw new SecurityException();

        return conductorService.create(conductor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        conductorService.delete(id);
    }

    }
