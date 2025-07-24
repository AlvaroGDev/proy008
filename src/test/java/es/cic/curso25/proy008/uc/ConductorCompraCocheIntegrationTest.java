package es.cic.curso25.proy008.uc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import es.cic.curso25.proy008.model.Coche;
import es.cic.curso25.proy008.model.Conductor;
import es.cic.curso25.proy008.service.CocheService;

@SpringBootTest
@AutoConfigureMockMvc
public class ConductorCompraCocheIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CocheService cocheService;

    @Test
    void testComprarCoche() throws Exception {
        /*
         * Primero se crea el Conductor
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
        
        coche.setConductor(conductor);
        // conductor.setCoche(coche);
        
        // Convertimos el objeto Coche que tiene Conductor en JSON
        String cocheJSON = objectMapper.writeValueAsString(coche);

        /*
         * Hacemos la solicitud HTTP para crear el Coche y a su vez el Conductor
         */
        MvcResult mvcResult = mockMvc.perform(post("/conductor/compra")
                .contentType("application/json")
                .content(cocheJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(resultado -> {
                    assertNotNull(
                        objectMapper.readValue(
                            resultado.getResponse().getContentAsString(), Coche.class)
                        );
                })
                .andReturn();
        
        Coche cocheCreado = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Coche.class);
        Long id = cocheCreado.getId();

        mockMvc.perform(get("/coche/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(resultado -> {
                    assertEquals(
                        objectMapper.readValue(resultado.getResponse().getContentAsString(), Coche.class).getId(), 
                        id);
                });
        
        cocheCreado.getConductor().setApellido("Acereda");

        String cocheCreadoJSON = objectMapper.writeValueAsString(cocheCreado);

        mockMvc.perform(put("/coche")
                .contentType("application/json")
                .content(cocheCreadoJSON))
                .andDo(print())
                .andExpect(status().isOk());
                
        mockMvc.perform(delete("/coche/" + id))
                .andDo(print())
                .andExpect(status().isOk());        
    }
}
