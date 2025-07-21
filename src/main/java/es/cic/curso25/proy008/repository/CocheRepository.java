package es.cic.curso25.proy008.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.cic.curso25.proy008.model.Coche;

@Repository
public interface CocheRepository extends JpaRepository<Coche, Long> {

}
