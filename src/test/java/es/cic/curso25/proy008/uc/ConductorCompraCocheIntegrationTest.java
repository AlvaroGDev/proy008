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

        Conductor miConductor = new Conductor();
        miConductor.setNombre("Manolo");
        miConductor.setApellido("Testeador");
        miConductor.setTfno("123654987");
        miConductor.setEmail("test@cic.es");
        miConductor.setGenero("M");

        String miConductorJSON = objectMapper.writeValueAsString(miConductor);

        mockMvc.perform(post("/conductor")
                .contentType("application/json")
                .content(miConductorJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(resultado -> {
                    String respuesta = resultado.getResponse().getContentAsString();
                    Conductor conductorPrueba = objectMapper.readValue(respuesta, Conductor.class);
                    assertTrue(conductorPrueba.getId() > 0, "El valor debe ser mayor que 0");

                    Optional<Conductor> revisarConductor = conductorRepository.findById(conductorPrueba.getId());
                    assertTrue(revisarConductor.isPresent());
                });

        /*
         * Después se crea el Coche
         */

        Coche coche = new Coche();
        coche.setMarca("Ford");
        coche.setNumPlazas(5);
        coche.setNumPuertas(5);
        coche.setTipoCombustible("Diesel");

        // Convierte el objeto Coche a formato JSON para enviarlo en la petición
        String cocheJson = objectMapper.writeValueAsString(coche);

        // Se realiza una petición POST simulada al endpoint /coche con los datos del
        // coche en formato JSON
        mockMvc.perform(post("/coche")
                .contentType("application/json")
                .content(cocheJson))
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
