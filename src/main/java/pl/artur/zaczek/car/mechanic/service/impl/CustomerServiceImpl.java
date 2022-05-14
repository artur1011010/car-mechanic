package pl.artur.zaczek.car.mechanic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.artur.zaczek.car.mechanic.jpa.AddressRepository;
import pl.artur.zaczek.car.mechanic.jpa.CustomerRepository;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.rest.error.NotFoundException;
import pl.artur.zaczek.car.mechanic.rest.model.CreateCustomer;
import pl.artur.zaczek.car.mechanic.rest.model.CustomerResponse;
import pl.artur.zaczek.car.mechanic.rest.model.SetCustomer;
import pl.artur.zaczek.car.mechanic.service.CustomerService;
import pl.artur.zaczek.car.mechanic.utils.CustomerMapper;
import pl.artur.zaczek.car.mechanic.utils.ModelValidator;

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
    private final ModelValidator modelValidator;

    @Override
    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long createCustomer(final CreateCustomer customerRequest) {
        modelValidator.validateCreateCustomerRequest(customerRequest);
        final Customer customer = customerMapper.createCustomerRequestToCustomerMapper(customerRequest);
        customer.setVehicleSet(new HashSet<>());
        addressRepository.save(customer.getAddress());
        final Customer saved = customerRepository.save(customer);
        return saved.getId();
    }

    @Override
    public CustomerResponse getCustomerById(final Long customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::customerToResponse)
                .orElseThrow(() -> {
                    log.error("Customer not found for user id:{}", customerId);
                    throw new NotFoundException("Customer with id: " + customerId + " not found", HttpStatus.NOT_FOUND.name());
                });
    }

    @Override
    @Transactional
    public void setCustomer(final SetCustomer customerRequest) {
        modelValidator.validateSetCustomerRequest(customerRequest);
        long customerId = customerRequest.getId();
        final Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.error("Customer not found for user id:{}", customerId);
                    throw new NotFoundException("Customer with id: " + customerId + " not found", HttpStatus.NOT_FOUND.name());
                });
        if(customerRequest.getAddress() != null){
            customer.setAddress(customerMapper.addressDTOToAddress(customerRequest.getAddress()));
        }
        customer.setCompany(customerRequest.isCompany());
        customer.setCompanyName(customerRequest.getCompanyName());
        customer.setCompanyNip(customerRequest.getCompanyNip());
        customer.setName(customerRequest.getName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNo(customerRequest.getPhoneNo());
        customer.setSecondPhoneNo(customerRequest.getSecondPhoneNo());
        customerRepository.save(customer);
    }
}
