package pl.artur.zaczek.car.mechanic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.artur.zaczek.car.mechanic.jpa.VehicleRepository;
import pl.artur.zaczek.car.mechanic.rest.model.VehicleResponse;
import pl.artur.zaczek.car.mechanic.service.VehicleService;
import pl.artur.zaczek.car.mechanic.utils.VehicleMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
private final VehicleMapper vehicleMapper;

    @Override
    public List<VehicleResponse> getVehicles() {
        return vehicleRepository.findAll().stream()
                .map(vehicleMapper::vehicleToResponse)
                .collect(Collectors.toList());
    }
}
