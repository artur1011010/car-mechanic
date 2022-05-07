package pl.artur.zaczek.car.mechanic.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.model.Vehicle;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
