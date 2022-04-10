package pl.artur.zaczek.car.mechanic.service;

import pl.artur.zaczek.car.mechanic.rest.model.VehicleResponse;

import java.util.List;

public interface VehicleService {
    List<VehicleResponse> getVehicles();
}
