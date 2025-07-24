package es.cic.curso25.proy008.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso25.proy008.controller.ViajeController;
import es.cic.curso25.proy008.repository.ViajeRepository;
import es.cic.curso25.proy008.model.Viaje;


   /*
    * El transactional, de base, lo que hace es que si está tratando con
    * información en la base de datos (crea, modifica, borra)
    * sólo ejecute el comando una vez ha visto que está todo bien y sale sin
    * errores
    * Si por ejemplo está actualizando un registro y a mitad peta, saca una
    * excepción y no toca nada
    * 
    * El readOnly = true se utiliza para optimizar tareas de lectura. Si se intenta
    * hacer algo que no sea lectura con esos métodos
    * devolverá una excepción
    */

@Service
@Transactional // Por lo general queremos siempre el import más generico, pero en este caso es
               // mejor el de Spring
public class ViajeService {

   private final static Logger LOGGER = LoggerFactory.getLogger(ViajeController.class);

   @Autowired
   private ViajeRepository viajeRepository;

   @Transactional(readOnly = true)
   public List<Viaje> get() {
      LOGGER.info(("Leo todos los viajes"));

      return viajeRepository.findAll();
   }

   @Transactional(readOnly = true)
   public Optional<Viaje> get(Long id) {
      LOGGER.info(("Leo el viaje con id: " + id));

      Optional<Viaje> viaje = viajeRepository.findById(id);

      return viaje;
   }

   public Viaje create(Viaje viaje) {
      LOGGER.info("Creo un viaje");
      viajeRepository.save(viaje);

      return viaje;
   }

   public Viaje update(Viaje viaje) {
      LOGGER.info("Actualizo un viaje");

      viajeRepository.save(viaje);

      return viaje;
   }

   public void delete(Long id) {
      LOGGER.info(("Borro un viaje"));

      viajeRepository.deleteById(id);
   }

}
