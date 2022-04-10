package pl.arturzaczek.carMechanicDB.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.arturzaczek.carMechanicDB.jpa.VehicleRepository;
import pl.arturzaczek.carMechanicDB.rest.model.VehicleResponse;
import pl.arturzaczek.carMechanicDB.service.VehicleService;
import pl.arturzaczek.carMechanicDB.utils.VehicleMapper;

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
