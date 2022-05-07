package pl.artur.zaczek.car.mechanic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.artur.zaczek.car.mechanic.jpa.CustomerRepository;
import pl.artur.zaczek.car.mechanic.jpa.VehicleRepository;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.model.Vehicle;
import pl.artur.zaczek.car.mechanic.rest.error.NotFoundException;
import pl.artur.zaczek.car.mechanic.rest.model.CreateVehicle;
import pl.artur.zaczek.car.mechanic.rest.model.VehicleResponse;
import pl.artur.zaczek.car.mechanic.service.VehicleService;
import pl.artur.zaczek.car.mechanic.utils.VehicleMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public List<VehicleResponse> getVehicles(final Optional<Long> customerId) {
        if (customerId.isPresent()) {
            final Long id = customerId.get();
            final Customer customer = customerRepository.findById(id).orElseThrow(() -> {
                log.error("Customer not found with id:{}", id);
                throw new NotFoundException("Customer with id: " + id + " not found", HttpStatus.NOT_FOUND.name());
            });
            return customer.getVehicleSet().stream()
                    .map(vehicleMapper::vehicleToResponse)
                    .collect(Collectors.toList());
        }
        return vehicleRepository.findAll().stream()
                .map(vehicleMapper::vehicleToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponse getVehicleById(final Long id) {
        return vehicleRepository.findById(id)
                .map(vehicleMapper::vehicleToResponse)
                .orElseThrow(() -> {
                    log.error("Vehicle not found with id:{}", id);
                    throw new NotFoundException("Vehicle with id: " + id + " not found", HttpStatus.NOT_FOUND.name());
                });
    }

    @Override
    @Transactional
    public Long createVehicle(final CreateVehicle request, final Long customerId) {
        final Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
            log.error("Customer not found with id:{}", customerId);
            throw new NotFoundException("Customer with id: " + customerId + " not found", HttpStatus.NOT_FOUND.name());
        });
        final Vehicle vehicle = vehicleMapper.createVehicleToVehicle(request);
        vehicle.setCustomers(Set.of(customer));
        customer.getVehicleSet().add(vehicle);
        final long id = vehicleRepository.save(vehicle).getId();
        customerRepository.save(customer);
        return id;
    }
}
