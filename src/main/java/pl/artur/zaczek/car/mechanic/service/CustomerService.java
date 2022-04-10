package pl.artur.zaczek.car.mechanic.service;

import pl.artur.zaczek.car.mechanic.rest.model.CreateCustomerRequest;
import pl.artur.zaczek.car.mechanic.rest.model.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getCustomers();

    Long createUser(CreateCustomerRequest customerRequest);

    CustomerResponse getCustomer(Long id);
}
