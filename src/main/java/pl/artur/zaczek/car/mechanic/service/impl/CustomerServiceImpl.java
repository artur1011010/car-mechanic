package pl.artur.zaczek.car.mechanic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.artur.zaczek.car.mechanic.jpa.AddressRepository;
import pl.artur.zaczek.car.mechanic.jpa.CustomerRepository;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.rest.error.BadRequestException;
import pl.artur.zaczek.car.mechanic.rest.model.CreateCustomerRequest;
import pl.artur.zaczek.car.mechanic.rest.model.CustomerResponse;
import pl.artur.zaczek.car.mechanic.service.CustomerService;
import pl.artur.zaczek.car.mechanic.utils.CustomerMapper;
import pl.artur.zaczek.car.mechanic.rest.error.NotFoundException;

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
        validateCreateCustomerRequest(customerRequest);
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

    private void validateCreateCustomerRequest(final CreateCustomerRequest customerRequest) {
        if (customerRequest == null) {
            throw new BadRequestException("customer request can not be null", HttpStatus.BAD_REQUEST.toString());
        }
        if (customerRequest.isCompany() || customerRequest.getCompanyNip() == null || customerRequest.getCompanyName() == null) {
            throw new BadRequestException("customer type company requires companyNip and companyName", HttpStatus.BAD_REQUEST.toString());
        }
    }
}
