package es.cic.curso25.proy008.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:mifichero.properties")
public class MiConfiguracion {
    @Value("${otraFuncionalidad}")
    private int otraFuncionalidad; //Lo ponemos en int porque es requerido (no puede ser nulo), si no fuese requerido, pondríamos Integer (puede ser null)

    @Bean
    public MiClaseCompleja miClaseCompleja(){
        // Lógica de construcción
        return new MiClaseCompleja();
    }

    // Cuando hacemos un @Autowired primero intenta buscar en el controlador, servicio o repositorio
    // Con el bean le permitimos que después de buscar en esos sitios, busque con las anotaciones de @Bean y haga los constructores
}
