package es.cic.curso25.proy008.uc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import es.cic.curso25.proy008.model.Coche;
import es.cic.curso25.proy008.model.Conductor;
import es.cic.curso25.proy008.repository.ConductorRepository;
import es.cic.curso25.proy008.service.CocheService;

@SpringBootTest
@AutoConfigureMockMvc
public class ConductorCompraCocheIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ConductorRepository conductorRepository;

    @Autowired
    CocheService cocheService;

    @Test
    void testComprarCoche() throws Exception {

        /*
         * Primero se crea un Conductor
         */
        Conductor conductor = new Conductor();
        conductor.setNombre("Manolo");
        conductor.setApellido("Testeador");
        conductor.setTfno("123654987");
        conductor.setEmail("test@cic.es");
        conductor.setGenero("M");
        
        /*
        * Después se crea el Coche
        */        
        Coche coche = new Coche();
        coche.setMarca("Ford");
        coche.setNumPlazas(5);
        coche.setNumPuertas(5);
        coche.setTipoCombustible("Diesel");

        conductor.setCoche(coche);
        coche.setConductor(conductor);
        
        String conductorJSON = objectMapper.writeValueAsString(conductor);
        String cocheJSON = objectMapper.writeValueAsString(coche);

        /*
         * Se hace el INSERT del Coche y del Conductor
         */
        mockMvc.perform(post("/conductor")
                .contentType("application/json")
                .content(conductorJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(resultado -> {
                    String respuesta = resultado.getResponse().getContentAsString();
                    Conductor conductorPrueba = objectMapper.readValue(respuesta, Conductor.class);
                    assertTrue(conductorPrueba.getId() > 0, "El valor debe ser mayor que 0");

                    Optional<Conductor> revisarConductor = conductorRepository.findById(conductorPrueba.getId());
                    assertTrue(revisarConductor.isPresent());
                });

        mockMvc.perform(post("/coche")
                .contentType("application/json")
                .content(cocheJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    // Extrae el contenido de la respuesta como string
                    String respuesta = result.getResponse().getContentAsString();
                    // Convierte la respuesta JSON en tipo Long y después se busca el coche con ese
                    // Id
                    // Coche cocheCreado = objectMapper.readValue(respuesta, Coche.class);
                    Long idCoche = objectMapper.readValue(respuesta, Long.class);
                    Optional<Coche> cocheCreado = cocheService.get(idCoche);
                    // Verifica que el coche creado tiene un ID no nulo
                    assertTrue(cocheCreado.get().getId() != null, "El valor debe de ser no nulo");
                    // Verifica que el coche creado está presente en el sistema
                    assertTrue(cocheCreado.isPresent());
                });
    }
}
