package es.cic.curso25.proy008.uc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso25.proy008.model.Conductor;
import es.cic.curso25.proy008.model.Viaje;
import es.cic.curso25.proy008.repository.ViajeRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ConductorHaceViaje {

        @Autowired
        ViajeRepository viajeRepository;

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        Conductor miConductor;
        Viaje miViaje;
        String viajeACrearJSON;

        @BeforeEach
        public void crearConductorViaje() throws Exception {

                miConductor = new Conductor();
                miViaje = new Viaje();

                miConductor.setNombre("Álvaro");
                miConductor.setApellido("Testeador");
                miConductor.setTfno("111222333");
                miConductor.setEmail("test@cic.es");
                miConductor.setGenero("M");

                miViaje.setDestino("Suecia");
                miViaje.setDistanciaKm(2982);
                miViaje.setEstado("Pendiente");
                miViaje.setFecha("16/11/2026");

                miViaje.setConductor(miConductor);
                miConductor.setViaje(miViaje);

                viajeACrearJSON = objectMapper.writeValueAsString(miViaje);

        }

        @Test
        public void testCreateConductorViaje() throws Exception {

                MvcResult mvcResult = mockMvc.perform(post("/conductor/generaviaje")
                                .contentType("application/json")
                                .content(viajeACrearJSON))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(conductorResultado -> {
                                        assertNotNull(objectMapper.readValue(
                                                        conductorResultado.getResponse().getContentAsString(),
                                                        Viaje.class),
                                                        "Viaje añadido a la lista");
                                })
                                .andReturn(); // Con esto me guardo toooodo el resultado que devuelve en un json

                Viaje viajeCreado = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Viaje.class);
                // Me creo un objeto viaje y le paso lo que en teoría hemos creado con el POST
                Long id = viajeCreado.getId(); // Me guardo su id

                mockMvc.perform(get("/viaje/" + id))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(viajeResultado -> {
                                        assertEquals(objectMapper.readValue(
                                                        viajeResultado.getResponse().getContentAsString(), Viaje.class)
                                                        .getId(),
                                                        id);
                                        /*
                                         * Haces un get del tipo buscar un registro por su id
                                         * y compruebas que sean iguales dos valores:
                                         * - El id que lo consigues de lo que te devuelve el objectMapper, como string,
                                         * y todo con la estructura de viaje
                                         * - El id que hemos cogido del viaje creado
                                         * Deberían ser el mismo ya que hemos creado un viaje, lo hemos insertado,
                                         * después hemos guardado su id
                                         * Si hacemos un get con ese id, nos devuelve el objeto y comprobamos que el id
                                         * es el mismo, está creado
                                         */
                                });

                // Hasta aquí tenemos comprobado que crea y lee
        }

        @Test
        public void testUpdateConductorViaje() throws Exception {

                // Creo la entidad relacionada como siempre

                MvcResult mvcResult = mockMvc.perform(post("/conductor/generaviaje")
                                .contentType("application/json")
                                .content(viajeACrearJSON))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(viajeResultado -> {
                                        assertNotNull(objectMapper.readValue(
                                                        viajeResultado.getResponse().getContentAsString(),
                                                        Viaje.class),
                                                        "Viaje añadido a la lista");
                                })
                                .andReturn(); // Con esto me guardo toooodo el resultado que devuelve en un json

                Viaje viajeCreado = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Viaje.class);

                // Creo un viaje con la relación hecha (Este viaje tiene dentro un conductor)

                viajeCreado.getConductor().setApellido("MiNuevoApellidoEsDeLaMancha");

                // Cambio el apellido del conductor

                viajeACrearJSON = objectMapper.writeValueAsString(viajeCreado);

                // Serializo de nuevo

                mockMvc.perform(put("/viaje")
                                .contentType("application/json")
                                .content(viajeACrearJSON))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(viajeResultado -> {
                                        assertEquals(objectMapper
                                                        .readValue(viajeResultado.getResponse().getContentAsString(),
                                                                        Viaje.class)
                                                        .getConductor()
                                                        .getApellido(), "MiNuevoApellidoEsDeLaMancha");
                                });

                /*
                 * Mando el objeto entero para hacer el PUT, y por pasos lo que hace es:
                 * Recoge la respuesta (el objeto entero modificado), lo recibe como String y
                 * con el objectMapper lo pasa a un objeto Viaje
                 * Después de esas conversiones, utiliza los getters para entrar primero en
                 * Conductor y luego en Apellido, y lo compara con el nuevo apellido
                 */
        }

        @Test
        public void testDeleteConductorViaje() throws Exception {

                MvcResult mvcResult = mockMvc.perform(post("/conductor/generaviaje")
                                .contentType("application/json")
                                .content(viajeACrearJSON))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(viajeResultado -> {
                                        assertNotNull(objectMapper.readValue(
                                                        viajeResultado.getResponse().getContentAsString(),
                                                        Viaje.class),
                                                        "Viaje añadido a la lista");
                                })
                                .andReturn();

                Long idGenerado = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Viaje.class)
                                .getId();

                mockMvc.perform(delete("/viaje/" + idGenerado))
                                .andExpect(status().isOk());

                assertTrue(viajeRepository.findById(idGenerado).isEmpty());

        }

}
