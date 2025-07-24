package es.cic.curso25.proy008.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:mifichero.properties")
public class MiConfiguracion {
    @Value("${otrafuncionalidad}")
    private int otrafuncionalidad;

    @Bean
    public MiClaseCompleja MiClaseCompleja(){
        // Lógica de construcción
        return new MiClaseCompleja();
    }
}
