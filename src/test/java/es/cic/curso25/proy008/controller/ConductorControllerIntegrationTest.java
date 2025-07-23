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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso25.proy008.model.Conductor;
import es.cic.curso25.proy008.repository.ConductorRepository;
import es.cic.curso25.proy008.service.ConductorService;

@SpringBootTest
@AutoConfigureMockMvc
public class ConductorControllerIntegrationTest {

        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @Autowired
        ConductorRepository conductorRepository;

        @Autowired
        ConductorService conductorService;

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
                                .andDo(print())
                                .andExpect(status().isOk());

                mockMvc.perform(get("/conductor/1")
                                .contentType("application/json")
                                .content(miConductorJSON))
                                .andDo(print())
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
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(resultado -> {
                                        String respuesta = resultado.getResponse().getContentAsString();
                                        Conductor conductorPrueba = objectMapper.readValue(respuesta, Conductor.class);
                                        assertTrue(conductorPrueba.getId() > 0, "El valor debe ser mayor que 0");

                                        Optional<Conductor> revisarConductor = conductorRepository
                                                        .findById(conductorPrueba.getId());
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
                                .andDo(print())
                                .andExpect(status().isOk());

                // Revisamos que esté

                mockMvc.perform(get("/conductor/1"))
                                .andDo(print())
                                .andExpect(status()
                                                .isOk());

                // La borramos

                mockMvc.perform(delete("/conductor/1"))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andReturn();

                // No haría falta revisar si no está, ya que el delete, si lo encuentra lo
                // borra, y si no lo encuentra lo ignora

        }

        @Test
        public void testUpdate() throws Exception {

                Conductor miConductor = new Conductor();

                miConductor.setNombre("Manolo");
                miConductor.setApellido("el borrador");
                miConductor.setTfno("123654987");
                miConductor.setEmail("testBorrado@cic.es");
                miConductor.setGenero("M");

                // Creamos tarea

                conductorService.create(miConductor);

                // Le cambiamos el valor

                miConductor.setNombre("NombreNuevoAntonioJose");
                // Lo volvemos a pasar a JSON

                String miConductorJSON = objectMapper.writeValueAsString(miConductor);

                mockMvc.perform(put("/conductor")
                                .contentType("application/json")
                                .content(miConductorJSON))
                                .andDo(print())
                                .andExpect(status().isOk());

                assertEquals(miConductor.getNombre(), "NombreNuevoAntonioJose");

        }

}
