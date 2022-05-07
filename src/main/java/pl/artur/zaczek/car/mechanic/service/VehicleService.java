package pl.artur.zaczek.car.mechanic.service;

import pl.artur.zaczek.car.mechanic.rest.model.CreateVehicle;
import pl.artur.zaczek.car.mechanic.rest.model.VehicleResponse;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    List<VehicleResponse> getVehicles(final Optional<Long> customerId);
    VehicleResponse getVehicleById(final Long id);
    Long createVehicle(final CreateVehicle request, final Long customerId);
}
