package pl.arturzaczek.carMechanicDB.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.arturzaczek.carMechanicDB.jpa.CustomerRepository;
import pl.arturzaczek.carMechanicDB.jpa.ServiceRequestRepository;
import pl.arturzaczek.carMechanicDB.model.Customer;
import pl.arturzaczek.carMechanicDB.rest.error.NotFoundException;
import pl.arturzaczek.carMechanicDB.rest.model.ServiceRequestResponse;
import pl.arturzaczek.carMechanicDB.service.ServiceRequestService;
import pl.arturzaczek.carMechanicDB.utils.ServiceRequestMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final CustomerRepository customerRepository;
    private final ServiceRequestMapper serviceRequestMapper;

    @Override
    public List<ServiceRequestResponse> findSRByCustomerId(final Long id) {
        final Optional<Customer> customerOptional = customerRepository.findById(id);
        final Customer customer = customerOptional.orElseThrow(() -> new NotFoundException("404", "User with id: " + id + " not found"));
        return serviceRequestRepository
                .findServiceRequestByCustomer(customer)
                .stream()
                .map(serviceRequestMapper::ToServiceRequestResponse)
                .collect(Collectors.toList());
    }
}
