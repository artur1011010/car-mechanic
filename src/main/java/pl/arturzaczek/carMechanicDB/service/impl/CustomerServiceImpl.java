package pl.arturzaczek.carMechanicDB.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.arturzaczek.carMechanicDB.jpa.AddressRepo;
import pl.arturzaczek.carMechanicDB.jpa.CustomerRepo;
import pl.arturzaczek.carMechanicDB.model.Customer;
import pl.arturzaczek.carMechanicDB.rest.model.CreateCustomerRequest;
import pl.arturzaczek.carMechanicDB.rest.model.CustomerResponse;
import pl.arturzaczek.carMechanicDB.service.CustomerService;
import pl.arturzaczek.carMechanicDB.utils.CustomerMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final AddressRepo addressRepo;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerResponse> getCustomers() {
        return customerRepo.findAll()
                .stream()
                .map(customerMapper::customerToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Long createUser(final CreateCustomerRequest customerRequest) {
        final Customer customer = customerMapper.createCustomerRequestToCustomerMapper(customerRequest);
        addressRepo.save(customer.getAddress());
        final Customer saved = customerRepo.save(customer);
        return saved.getId();
    }
}
