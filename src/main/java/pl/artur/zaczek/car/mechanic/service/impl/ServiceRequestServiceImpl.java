package pl.artur.zaczek.car.mechanic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.artur.zaczek.car.mechanic.jpa.CustomerRepository;
import pl.artur.zaczek.car.mechanic.jpa.ServiceRequestRepository;
import pl.artur.zaczek.car.mechanic.jpa.VehicleRepository;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.model.ServiceRequest;
import pl.artur.zaczek.car.mechanic.model.Vehicle;
import pl.artur.zaczek.car.mechanic.rest.model.CreateServiceRequest;
import pl.artur.zaczek.car.mechanic.rest.error.NotFoundException;
import pl.artur.zaczek.car.mechanic.rest.model.ServiceRequestResponse;
import pl.artur.zaczek.car.mechanic.service.ServiceRequestService;
import pl.artur.zaczek.car.mechanic.utils.ServiceRequestMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final CustomerRepository customerRepository;
    private final ServiceRequestMapper serviceRequestMapper;
    private final VehicleRepository vehicleRepository;

    @Override
    public List<ServiceRequestResponse> findSRByCustomerId(final Long userId) {
        final Customer customer = customerRepository
                .findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found for user id:{}", userId);
                    throw new NotFoundException("404", "User with id: " + userId + " not found");
                });
        return serviceRequestRepository
                .findServiceRequestByCustomer(customer)
                .stream()
                .map(serviceRequestMapper::ToServiceRequestResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Long createSR(final CreateServiceRequest request, final Long userId, final Long vehicleId) {
        final Customer customer = customerRepository
                .findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found for user id:{}\nrequest:{}", userId, request);
                    throw new NotFoundException("404", "User with id: " + userId + " not found");
                });

        final Vehicle vehicle = vehicleRepository
                .findById(vehicleId)
                .orElseThrow(() -> {
                    log.error("Vehicle not found for id:{}\nrequest:{}", vehicleId, request);
                    throw new NotFoundException("404", "Vehicle with id: " + vehicleId + " not found");
                });
        final ServiceRequest serviceRequest = serviceRequestMapper.ToServiceRequest(request);
        serviceRequest.setCustomer(customer);
        serviceRequest.setVehicle(vehicle);
        serviceRequestRepository.save(serviceRequest);
        return serviceRequest.getId();
    }
}
