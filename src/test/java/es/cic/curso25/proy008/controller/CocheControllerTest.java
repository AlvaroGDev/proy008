package es.cic.curso25.proy008.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso25.proy008.model.Coche;
import es.cic.curso25.proy008.repository.CocheRepository;
import es.cic.curso25.proy008.service.CocheService;

@SpringBootTest
@AutoConfigureMockMvc
public class CocheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CocheRepository cocheRepository;

    @Autowired
    private CocheService cocheService;

    @AfterEach
    void limpiarBaseDeDatos() {
        cocheRepository.deleteAll();
    }

    @Test
    void testCreate() throws Exception {
        Coche coche = new Coche();
        coche.setMarca("Ford");
        coche.setNumPlazas(5);
        coche.setNumPuertas(5);
        coche.setTipoCombustible("Diesel");

        // Convierte el objeto Coche a formato JSON para enviarlo en la petición
        String cocheJson = objectMapper.writeValueAsString(coche);

        // Se realiza una petición POST simulada al endpoint /coche con los datos del coche en formato JSON
        mockMvc.perform(post("/coche")
                .contentType("application/json")
                .content(cocheJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    // Extrae el contenido de la respuesta como string
                    String respuesta = result.getResponse().getContentAsString();
                    // Convierte la respuesta JSON en tipo Long y después se busca el coche con ese Id
                    // Coche cocheCreado = objectMapper.readValue(respuesta, Coche.class);
                    Long idCoche = objectMapper.readValue(respuesta,Long.class);
                    Optional<Coche> cocheCreado = cocheService.get(idCoche);
                    // Verifica que el coche creado tiene un ID no nulo
                    assertTrue(cocheCreado.get().getId() != null, "El valor debe de ser no nulo");
                    // Verifica que el coche creado está presente en el sistema
                    assertTrue(cocheCreado.isPresent());
                });
    }
    
    @Test
    void testGet() throws Exception {
        Coche coche = new Coche();
        coche.setMarca("Ford");
        coche.setNumPlazas(5);
        coche.setNumPuertas(5);
        coche.setTipoCombustible("Diesel");

        cocheService.create(coche);

        mockMvc.perform(get("/coche/" + coche.getId())
                                .contentType("application/json"))
                                // Validar el estado HTTP
                                .andExpect(status().isOk())
                                // Validar el contenido del JSON
                                .andExpect(result -> {
                                    // Extrae el contenido de la respuesta como string
                                    String respuesta = result.getResponse().getContentAsString();
                                    // Convierte la respuesta JSON en un objeto Coche
                                    Coche cocheReturned = objectMapper.readValue(respuesta, Coche.class);
                                    // Verifica que el coche retornado sea el correcto a traves de la comparación de uno de sus campos
                                    assertEquals("Ford", cocheReturned.getMarca());
                                });
    }
    
    @Test
    void testGetAll() throws Exception {
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

        mockMvc.perform(get("/coche")
                                .contentType("application/json"))
                                // Validar el estado HTTP
                                .andExpect(status().isOk())
                                // Validar el contenido del JSON
                                .andExpect(result -> {
                                    // Extrae el contenido de la respuesta como string
                                    String respuesta = result.getResponse().getContentAsString();
                                    // Convierte la respuesta JSON en un objeto Coche
                                    List<Coche> coches = objectMapper.readValue(respuesta, new TypeReference<List<Coche>>(){});
                                    // Verifica que el coche retornado sea el correcto a traves de la comparación de uno de sus campos
                                    assertEquals(2, coches.size());
                                });
    }
    
    @Test
    void testUpdate() throws Exception {
        Coche coche = new Coche();
        coche.setMarca("Ford");
        coche.setNumPlazas(5);
        coche.setNumPuertas(5);
        coche.setTipoCombustible("Diesel");

        cocheService.create(coche);

        coche.setMarca("Seat");
        cocheService.update(coche);

        // Convierte el objeto Coche a formato JSON para enviarlo en la petición
        String cocheJson = objectMapper.writeValueAsString(coche);

        mockMvc.perform(put("/coche")
            .contentType("application/json")
            .content(cocheJson))
            .andExpect(status().isOk());

        // Verifica que el coche fue atualizado correctamente
        assertEquals("Seat", coche.getMarca());
    }

    @Test
    void testDelete() throws Exception {
        Coche coche = new Coche();
        coche.setMarca("Ford");
        coche.setNumPlazas(5);
        coche.setNumPuertas(5);
        coche.setTipoCombustible("Diesel");

        Long idCoche = cocheService.create(coche);

        mockMvc.perform(delete("/coche/" + coche.getId()))
                                .andExpect(status().isOk());

        // Verifica que el coche ya no esta en la BBDD
        Optional<Coche> eliminado = cocheService.get(idCoche);
        assertTrue(eliminado.isEmpty()); // Ya no debería estar presente
    }
}
