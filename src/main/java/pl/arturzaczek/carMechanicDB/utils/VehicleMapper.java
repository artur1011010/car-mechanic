package pl.arturzaczek.carMechanicDB.utils;

import org.mapstruct.Mapper;
import pl.arturzaczek.carMechanicDB.model.Engine;
import pl.arturzaczek.carMechanicDB.model.Vehicle;
import pl.arturzaczek.carMechanicDB.rest.model.EngineResponse;
import pl.arturzaczek.carMechanicDB.rest.model.VehicleResponse;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface VehicleMapper {
    EngineResponse engineToResponse(Engine source);

    VehicleResponse vehicleToResponse(Vehicle source);
}
