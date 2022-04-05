package pl.arturzaczek.carMechanicDB.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.arturzaczek.carMechanicDB.jpa.CustomerRepo;
import pl.arturzaczek.carMechanicDB.model.Customer;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;

    public List<Customer> getCustomers(){
        return customerRepo.findAll();
    }

}
