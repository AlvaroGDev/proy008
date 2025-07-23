package es.cic.curso25.proy008.uc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso25.proy008.model.Conductor;
import es.cic.curso25.proy008.model.Viaje;

@SpringBootTest
@AutoConfigureMockMvc
public class ConductorHaceViaje {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testConductorHaceViaje() throws Exception {
        /*
          Primero se crea un conductor
         */

        Conductor miConductor = new Conductor();
        miConductor.setNombre("Álvaro");
        miConductor.setApellido("Testeador");
        miConductor.setTfno("111222333");
        miConductor.setEmail("test@cic.es");
        miConductor.setGenero("M");
        
        Viaje miViaje = new Viaje();

        miViaje.setDestino("Suecia");
        miViaje.setDistanciaKm(2982);
        miViaje.setEstado("Pendiente");
        miViaje.setFecha("16/11/2026");

        miViaje.setConductor(miConductor);
        miConductor.setViaje(miViaje);

        //Convertimos el objeto en json

        String viajeACrearJSON = objectMapper.writeValueAsString(miViaje);
        //Aquí tienes un json con los datos del viaje y dentro otro json con los datos de la persona
      
        mockMvc.perform(post("/conductor/generaviaje")
                .contentType("application/json")
                .content(viajeACrearJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                conductorResultado-> {
                assertNotNull(
                    objectMapper.readValue(
                        conductorResultado.getResponse().getContentAsString(), 
                                            Viaje.class), 
                                            "Viaje añadido a la lista");
                });
       
    }
    
}
