package pl.arturzaczek.carMechanicDB.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.arturzaczek.carMechanicDB.jpa.AddressRepository;
import pl.arturzaczek.carMechanicDB.jpa.CustomerRepository;
import pl.arturzaczek.carMechanicDB.model.Customer;
import pl.arturzaczek.carMechanicDB.rest.error.NotFoundException;
import pl.arturzaczek.carMechanicDB.rest.model.CreateCustomerRequest;
import pl.arturzaczek.carMechanicDB.rest.model.CustomerResponse;
import pl.arturzaczek.carMechanicDB.service.CustomerService;
import pl.arturzaczek.carMechanicDB.utils.CustomerMapper;

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
    public Long createUser(final CreateCustomerRequest customerRequest) {
        final Customer customer = customerMapper.createCustomerRequestToCustomerMapper(customerRequest);
        addressRepository.save(customer.getAddress());
        final Customer saved = customerRepository.save(customer);
        return saved.getId();
    }

    @Override
    public CustomerResponse getCustomer(Long userId) {
        return customerRepository.findById(userId)
                .map(customerMapper::customerToResponse)
                .orElseThrow(() -> {
                    log.error("User not found for user id:{}", userId);
                    throw new NotFoundException("404", "User with id: " + userId + " not found");
                });
    }
}
