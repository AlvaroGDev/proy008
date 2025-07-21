package es.cic.curso25.proy008.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
    void testCreate() throws Exception{
    
        Conductor miConductor = new Conductor();
        
        miConductor.setNombre("Manolo");
        miConductor.setApellido("Testeador");
        miConductor.setTfno("123654987");
        miConductor.setEmail("test@cic.es");
        miConductor.setGenero("M");
    }




}
