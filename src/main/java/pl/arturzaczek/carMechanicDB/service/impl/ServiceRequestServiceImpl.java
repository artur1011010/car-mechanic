package pl.arturzaczek.carMechanicDB.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.arturzaczek.carMechanicDB.jpa.CustomerRepository;
import pl.arturzaczek.carMechanicDB.jpa.ServiceRequestRepository;
import pl.arturzaczek.carMechanicDB.jpa.VehicleRepository;
import pl.arturzaczek.carMechanicDB.model.Customer;
import pl.arturzaczek.carMechanicDB.model.ServiceRequest;
import pl.arturzaczek.carMechanicDB.model.Vehicle;
import pl.arturzaczek.carMechanicDB.rest.error.NotFoundException;
import pl.arturzaczek.carMechanicDB.rest.model.CreateServiceRequest;
import pl.arturzaczek.carMechanicDB.rest.model.ServiceRequestResponse;
import pl.arturzaczek.carMechanicDB.service.ServiceRequestService;
import pl.arturzaczek.carMechanicDB.utils.ServiceRequestMapper;

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
        log.info("+\n+\n+\n+\n+\n{}", serviceRequest.getId());
        return serviceRequest.getId();
    }
}
