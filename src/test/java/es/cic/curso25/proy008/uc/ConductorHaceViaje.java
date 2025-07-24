package es.cic.curso25.proy008.uc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
         * Primero se crea un conductor
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

        // Convertimos el objeto en json

        String viajeACrearJSON = objectMapper.writeValueAsString(miViaje);
        // Aquí tienes un json con los datos del viaje y dentro otro json con los datos
        // de la persona

        MvcResult mvcResult = mockMvc.perform(post("/conductor/generaviaje")
                .contentType("application/json")
                .content(viajeACrearJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        conductorResultado -> {
                            assertNotNull(
                                    objectMapper.readValue(
                                            conductorResultado.getResponse().getContentAsString(),
                                            Viaje.class),
                                    "Viaje añadido a la lista");
                        })
                .andReturn(); // Con esto me guardo toooodo el resultado que devuelve en un json

        Viaje viajeCreado = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Viaje.class);
        // Me creo un objeto viaje y le paso lo que en teoría hemos creado con el POST
        Long id = viajeCreado.getId(); // Me guardo su id

        mockMvc.perform(get("/viaje/"+id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(viajeResultado -> {
                    assertEquals(objectMapper.readValue(
                            viajeResultado.getResponse().getContentAsString(), Viaje.class).getId(),
                            id);
                            /*
                             Haces un get del tipo buscar un registro por su id
                             y compruebas que sean iguales dos valores:
                             - El id que lo consigues de lo que te devuelve el objectMapper, como string, y todo con la estructura de viaje
                             - El id que hemos cogido del viaje creado
                             Deberían ser el mismo ya que hemos creado un viaje, lo hemos insertado, después hemos guardado su id
                             Si hacemos un get con ese id, nos devuelve el objeto y comprobamos que el id es el mismo, está creado
                             */
                });

        // Hasta aquí tenemos comprobado que crea y lee

        viajeCreado.getConductor().setNombre("EL NUEVO NOMBRE DEL CONDUCTOR");
        viajeCreado.getConductor().setApellido("Y EL APELLIDO TAMBIÉN");

        String viajeJSON = objectMapper.writeValueAsString(viajeCreado);

        mockMvc.perform(put("/viaje")
                        .contentType("application/json")
                        .content(viajeJSON))
                        .andDo(print())
                        .andExpect(status().isOk());

        mockMvc.perform(delete("/viaje/" + id))
                       .andDo(print()) 
                       .andExpect(status().isOk());  
                       

    }



    // HAY QUE COMPROBAR QUE ESTÁ todo, COMPROBANDO CON EL UPDATE Y DEMÁS

}
