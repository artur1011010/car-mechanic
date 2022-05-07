package pl.artur.zaczek.car.mechanic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.artur.zaczek.car.mechanic.jpa.AddressRepository;
import pl.artur.zaczek.car.mechanic.jpa.CustomerRepository;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.rest.error.BadRequestException;
import pl.artur.zaczek.car.mechanic.rest.error.NotFoundException;
import pl.artur.zaczek.car.mechanic.rest.model.CreateCustomer;
import pl.artur.zaczek.car.mechanic.rest.model.CustomerResponse;
import pl.artur.zaczek.car.mechanic.service.CustomerService;
import pl.artur.zaczek.car.mechanic.utils.CustomerMapper;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Long createUser(final CreateCustomer customerRequest) {
        validateCreateCustomerRequest(customerRequest);
        final Customer customer = customerMapper.createCustomerRequestToCustomerMapper(customerRequest);
        customer.setVehicleSet(new HashSet<>());
        addressRepository.save(customer.getAddress());
        final Customer saved = customerRepository.save(customer);
        return saved.getId();
    }

    @Override
    public CustomerResponse getCustomerById(final Long userId) {
        return customerRepository.findById(userId)
                .map(customerMapper::customerToResponse)
                .orElseThrow(() -> {
                    log.error("User not found for user id:{}", userId);
                    throw new NotFoundException("User with id: " + userId + " not found", HttpStatus.NOT_FOUND.name());
                });
    }

    private void validateCreateCustomerRequest(final CreateCustomer customerRequest) {
        if (customerRequest == null) {
            log.error("Customer request can not be null");
            throw new BadRequestException("Customer request can not be null", HttpStatus.BAD_REQUEST.toString());
        }
        if (customerRequest.isCompany() || customerRequest.getCompanyNip() == null || customerRequest.getCompanyName() == null) {
            log.error("Customer type company requires companyNip and companyName: {}", customerRequest);
            throw new BadRequestException("Customer type company requires companyNip and companyName", HttpStatus.BAD_REQUEST.toString());
        }
    }
}
