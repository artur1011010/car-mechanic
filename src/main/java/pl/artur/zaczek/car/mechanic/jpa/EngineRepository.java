package pl.artur.zaczek.car.mechanic.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.artur.zaczek.car.mechanic.model.Engine;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Long> {
}
