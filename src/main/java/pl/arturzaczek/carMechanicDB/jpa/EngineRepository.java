package pl.arturzaczek.carMechanicDB.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.arturzaczek.carMechanicDB.model.Engine;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Long> {
}
