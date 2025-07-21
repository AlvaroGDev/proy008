package es.cic.curso25.proy008.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.cic.curso25.proy008.model.Coche;
import es.cic.curso25.proy008.repository.CocheRepository;

@SpringBootTest
public class CocheServiceTest {

    @Autowired
    CocheService cocheService;

    @Autowired
    CocheRepository cocheRepository;

    @Test
    void testCreate() {
        Coche coche = new Coche();
        coche.setMarca("Ford");
        coche.setNumPlazas(5);
        coche.setNumPuertas(5);
        coche.setTipoCombustible("Diesel");

        Long idResult = cocheService.create(coche);
        assertTrue(idResult != 0, "El id de retorno es mayor que 0");
    }
    
    @Test
    void testGet() {
        Coche coche = new Coche();
        coche.setMarca("Ford");
        coche.setNumPlazas(5);
        coche.setNumPuertas(5);
        coche.setTipoCombustible("Diesel");

        Long idResult = cocheService.create(coche);

        Optional<Coche> cocheResult = cocheService.get(idResult);
        assertTrue(cocheResult.isPresent());
    }
    
    @Test
    void testGetAll() {
        Coche coche1 = new Coche();
        coche1.setMarca("Ford");
        coche1.setNumPlazas(5);
        coche1.setNumPuertas(5);
        coche1.setTipoCombustible("Diesel");

        cocheService.create(coche1);

        Coche coche2 = new Coche();
        coche2.setMarca("Seat");
        coche2.setNumPlazas(4);
        coche2.setNumPuertas(3);
        coche2.setTipoCombustible("Gasolina");

        cocheService.create(coche2);

        List<Coche> coches = cocheService.getAll();
        assertEquals(2, coches.size());
    }

    @Test
    void testUpdate() {
        Coche coche = new Coche();
        coche.setMarca("Ford");
        coche.setNumPlazas(5);
        coche.setNumPuertas(5);
        coche.setTipoCombustible("Diesel");

        cocheService.create(coche);

        coche.setMarca("Seat");

        cocheService.update(coche);
        assertEquals("Seat", coche.getMarca());
    }

    @Test
    void testDelete() {
        Coche coche = new Coche();
        coche.setMarca("Ford");
        coche.setNumPlazas(5);
        coche.setNumPuertas(5);
        coche.setTipoCombustible("Diesel");
    
        Long idResult = cocheService.create(coche);
        
        cocheService.delete(idResult);

        Optional<Coche> cocheEliminado = cocheService.get(idResult);
    
        assertTrue(!cocheEliminado.isPresent());
    }
}
