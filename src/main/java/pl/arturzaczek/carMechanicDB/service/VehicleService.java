package pl.arturzaczek.carMechanicDB.service;

import pl.arturzaczek.carMechanicDB.rest.model.VehicleResponse;

import java.util.List;

public interface VehicleService {
    List<VehicleResponse> getVehicles();
}
