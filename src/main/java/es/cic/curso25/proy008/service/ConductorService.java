package es.cic.curso25.proy008.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso25.proy008.model.Conductor;
import es.cic.curso25.proy008.repository.ConductorRepository;

@Service
@Transactional
public class ConductorService {

    @Autowired
    private ConductorRepository conductorRepository;

    public Conductor create(Conductor conductor) {

        return conductorRepository.save(conductor);
    }

    @Transactional(readOnly = true)
    public Conductor get(Long id) {
        Optional<Conductor> conductor = conductorRepository.findById(id);
        return conductor.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Conductor> get() {
        return conductorRepository.findAll();
    }

    public void update(Conductor conductor) {
        conductorRepository.save(conductor);
    }

    public void delete(Long id) {
        conductorRepository.deleteById(id);
    }

}
