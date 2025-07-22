package es.cic.curso25.proy008.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso25.proy008.model.Conductor;
import es.cic.curso25.proy008.repository.ConductorRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ConductorControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ConductorRepository conductorRepository;

    @Test
    void testGet() throws Exception {

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
                .andExpect(status().isOk());

        mockMvc.perform(get("/conductor/1")
                .contentType("application/json")
                .content(miConductorJSON))
                .andExpect(status().isOk());

    }

    @Test
    void testCreate() throws Exception {

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
                .andExpect(status().isOk())
                .andExpect(resultado -> {
                    String respuesta = resultado.getResponse().getContentAsString();
                    Conductor conductorPrueba = objectMapper.readValue(respuesta, Conductor.class);
                    assertTrue(conductorPrueba.getId() > 0, "El valor debe ser mayor que 0");

                    Optional<Conductor> revisarConductor = conductorRepository.findById(conductorPrueba.getId());
                    assertTrue(revisarConductor.isPresent());
                });
    }

    @Test
    void testDelete() throws Exception {

        Conductor miConductor = new Conductor();

        miConductor.setNombre("Manolo");
        miConductor.setApellido("el borrador");
        miConductor.setTfno("123654987");
        miConductor.setEmail("testBorrado@cic.es");
        miConductor.setGenero("M");

        String miConductorJSON = objectMapper.writeValueAsString(miConductor);

        // Creamos tarea

        mockMvc.perform(post("/conductor")
                .contentType("application/json")
                .content(miConductorJSON))
                .andExpect(status().isOk());

        // Revisamos que esté

        mockMvc.perform(get("/conductor/1"))
                .andExpect(status()
                        .isOk());

        // La borramos

        mockMvc.perform(delete("/conductor/1"))
                .andExpect(status().isOk())
                .andReturn();

        // Revisamos que esté

        mockMvc.perform(get("/conductor/1"))
                .andExpect(status()
                        .isOk());

    }

}
