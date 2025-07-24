package es.cic.curso25.proy008.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import com.fasterxml.jackson.databind.ObjectMapper;
import es.cic.curso25.proy008.configuration.MiClaseCompleja;
import es.cic.curso25.proy008.model.Conductor;
import es.cic.curso25.proy008.repository.ConductorRepository;
import es.cic.curso25.proy008.service.ConductorService;

@SpringBootTest
@AutoConfigureMockMvc
public class ConductorControllerIntegrationTest {

        @Autowired
        MiClaseCompleja miClaseCompleja;

        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @Autowired
        ConductorRepository conductorRepository;

        @Autowired
        ConductorService conductorService;

        String miConductorJSON;
        Conductor miConductor;

        @BeforeEach
        public void generaConductor() throws Exception {

                miConductor = new Conductor();
                miConductor.setNombre("Manolo");
                miConductor.setApellido("Testeador");
                miConductor.setTfno("123654987");
                miConductor.setEmail("test@cic.es");
                miConductor.setGenero("M");

                miConductorJSON = objectMapper.writeValueAsString(miConductor);
        }

        @Test
        void testCreate() throws Exception {

                mockMvc.perform(post("/conductor")
                                .contentType("application/json")
                                .content(miConductorJSON))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(resultado -> {
                                        assertTrue(
                                                objectMapper.readValue(resultado.getResponse().getContentAsString(),
                                                                        Conductor.class)
                                                                        .getId() > 0);
                                });
        }

        @Test
        public void testUpdate() throws Exception {

               
               MvcResult mvcResult = mockMvc.perform(post("/conductor")
                                .contentType("application/json")
                                .content(miConductorJSON))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andReturn();

                // Nos guardamos el objeto devuelto (en este caso el conductor con el id generado)


                // Cogemos el id generado, le cambiamos los datos y le añadimos el id
                Long idGenerado = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Conductor.class).getId();

                miConductor.setNombre("NombreNuevoAntonioJose");
                miConductor.setId(idGenerado);

                // Lo volvemos a pasar a JSON, esta vez con el ID
                miConductorJSON = objectMapper.writeValueAsString(miConductor);
 
               mockMvc.perform(put("/conductor")
                                .contentType("application/json")
                                .content(miConductorJSON))
                                .andDo(print())
                                .andExpect(status().isOk());

                assertEquals(miConductor.getNombre(), "NombreNuevoAntonioJose");

        }

        @Test
        void testDelete() throws Exception {

                 // Creamos conductor

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
        }

}
