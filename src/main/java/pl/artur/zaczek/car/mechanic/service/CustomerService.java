package pl.artur.zaczek.car.mechanic.service;

import pl.artur.zaczek.car.mechanic.rest.model.CreateCustomer;
import pl.artur.zaczek.car.mechanic.rest.model.CustomerResponse;
import pl.artur.zaczek.car.mechanic.rest.model.SetCustomer;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getCustomers();
    Long createCustomer(CreateCustomer customerRequest);
    CustomerResponse getCustomerById(Long id);
    void setCustomer(SetCustomer customerRequest);
}
