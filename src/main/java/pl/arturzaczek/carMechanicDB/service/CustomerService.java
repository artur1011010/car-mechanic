package pl.arturzaczek.carMechanicDB.service;

import pl.arturzaczek.carMechanicDB.rest.model.CreateCustomerRequest;
import pl.arturzaczek.carMechanicDB.rest.model.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getCustomers();

    Long createUser(CreateCustomerRequest customerRequest);
}
