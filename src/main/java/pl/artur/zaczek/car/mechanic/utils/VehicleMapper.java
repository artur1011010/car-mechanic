package pl.artur.zaczek.car.mechanic.utils;

import org.mapstruct.Mapper;
import pl.artur.zaczek.car.mechanic.model.Engine;
import pl.artur.zaczek.car.mechanic.model.Vehicle;
import pl.artur.zaczek.car.mechanic.rest.model.CreateVehicle;
import pl.artur.zaczek.car.mechanic.rest.model.EngineDTO;
import pl.artur.zaczek.car.mechanic.rest.model.EngineResponse;
import pl.artur.zaczek.car.mechanic.rest.model.VehicleResponse;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface VehicleMapper {
    EngineResponse engineToResponse(Engine source);

    VehicleResponse vehicleToResponse(Vehicle source);

    Vehicle createVehicleToVehicle(CreateVehicle source);

    Engine engineDTOToEngine(EngineDTO source);

}
