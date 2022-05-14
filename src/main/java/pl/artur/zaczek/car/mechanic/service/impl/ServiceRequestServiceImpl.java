package pl.artur.zaczek.car.mechanic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.artur.zaczek.car.mechanic.jpa.CustomerRepository;
import pl.artur.zaczek.car.mechanic.jpa.ServiceRequestRepository;
import pl.artur.zaczek.car.mechanic.jpa.VehicleRepository;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.model.ServiceRequest;
import pl.artur.zaczek.car.mechanic.model.Vehicle;
import pl.artur.zaczek.car.mechanic.rest.error.NotFoundException;
import pl.artur.zaczek.car.mechanic.rest.error.NotImplementedException;
import pl.artur.zaczek.car.mechanic.rest.model.CreateServiceRequest;
import pl.artur.zaczek.car.mechanic.rest.model.ServiceRequestResponse;
import pl.artur.zaczek.car.mechanic.rest.model.SetServiceRequest;
import pl.artur.zaczek.car.mechanic.service.ServiceRequestService;
import pl.artur.zaczek.car.mechanic.utils.ServiceRequestMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final String API_USER = "API_USER";

    private final ServiceRequestRepository serviceRequestRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceRequestMapper serviceRequestMapper;

    @Override
    public List<ServiceRequestResponse> findSR(final Optional<Long> customerId, final Optional<Long> vehicleId) {
        if (customerId.isEmpty() && vehicleId.isEmpty()) {
            return serviceRequestRepository.findAll().stream()
                    .map(serviceRequestMapper::toServiceRequestResponse)
                    .collect(Collectors.toList());
        } else if (vehicleId.isPresent() && customerId.isEmpty()) {
            return findSRByVehicleId(vehicleId.get());
        } else if (customerId.isPresent() && vehicleId.isEmpty()) {
            return findSRByCustomerId(customerId.get());
        } else {
            log.error("vehicleId:{} and customerId:{}", vehicleId, customerId);
            throw new NotImplementedException("VehicleId " + vehicleId.get() + " and customerId " + customerId.get(), HttpStatus.NOT_IMPLEMENTED.name());
        }
    }

    @Override
    public List<ServiceRequestResponse> findSRByCustomerId(final Long customerId) {
        final Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> {
                    log.error("Customer not found for customerId:{}", customerId);
                    throw new NotFoundException("Customer with id: " + customerId + " not found", HttpStatus.NOT_FOUND.name());
                });
        return serviceRequestRepository
                .findServiceRequestByCustomer(customer)
                .stream()
                .map(serviceRequestMapper::toServiceRequestResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceRequestResponse> findSRByVehicleId(final Long vehicleId) {
        final Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> {
                    log.error("Vehicle not found for vehicle id:{}", vehicleId);
                    throw new NotFoundException("Vehicle with id: " + vehicleId + " not found", HttpStatus.NOT_FOUND.name());
                });
        return serviceRequestRepository
                .findServiceRequestsByVehicle(vehicle)
                .stream()
                .map(serviceRequestMapper::toServiceRequestResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceRequestResponse getSRById(Long id) {
        return serviceRequestRepository
                .findById(id)
                .map(serviceRequestMapper::toServiceRequestResponse)
                .orElseThrow(() -> {
                    log.error("Service request not found with id:{}", id);
                    throw new NotFoundException("Service request id: " + id + " not found", HttpStatus.NOT_FOUND.name());
                });
    }

    @Override
    @Transactional
    public Long createSR(final CreateServiceRequest request, final Long customerId, final Long vehicleId) {
        final Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> {
                    log.error("Customer not found for customer id:{}\nrequest:{}", customerId, request);
                    throw new NotFoundException("Customer with id: " + customerId + " not found", HttpStatus.NOT_FOUND.name());
                });
        final Vehicle vehicle = vehicleRepository
                .findById(vehicleId)
                .orElseThrow(() -> {
                    log.error("Vehicle not found for id:{}\nrequest:{}", vehicleId, request);
                    throw new NotFoundException("Vehicle with id: " + vehicleId + " not found", HttpStatus.NOT_FOUND.name());
                });
        final ServiceRequest serviceRequest = serviceRequestMapper.toServiceRequest(request);
        vehicle.addCustomer(customer);
        serviceRequest.setCustomer(customer);
        serviceRequest.setVehicle(vehicle);
        serviceRequest.setCreatedTime(LocalDateTime.now());
        serviceRequest.setCreatedUser(API_USER);
        serviceRequestRepository.save(serviceRequest);
        vehicleRepository.save(vehicle);
        return serviceRequest.getId();
    }

    @Override
    @Transactional
    public void setSR(final SetServiceRequest request) {
        final long srId = request.getId();
        final ServiceRequest serviceRequest = serviceRequestRepository
                .findById(srId)
                .orElseThrow(() -> {
                    log.error("Service request not found with id:{}", srId);
                    throw new NotFoundException("Service request id: " + srId + " not found", HttpStatus.NOT_FOUND.name());
                });
        serviceRequest.setModifiedTime(LocalDateTime.now());
        serviceRequest.setModifiedUser(API_USER);
        serviceRequest.setTitle(request.getTitle());
        serviceRequest.setComment(request.getComment());
        serviceRequest.setStartTime(request.getStartTime());
        serviceRequest.setFinishTime(request.getFinishTime());
        serviceRequest.setDone(request.isDone());
        serviceRequest.setPrice(request.getPrice());
        serviceRequest.setDiscount(request.getDiscount());
        serviceRequestRepository.save(serviceRequest);
    }
}
