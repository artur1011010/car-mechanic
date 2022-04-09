package pl.arturzaczek.carMechanicDB.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.arturzaczek.carMechanicDB.jpa.AddressRepository;
import pl.arturzaczek.carMechanicDB.jpa.CustomerRepository;
import pl.arturzaczek.carMechanicDB.model.Customer;
import pl.arturzaczek.carMechanicDB.model.ServiceRequest;
import pl.arturzaczek.carMechanicDB.model.Vehicle;
import pl.arturzaczek.carMechanicDB.rest.model.CustomerResponse;
import pl.arturzaczek.carMechanicDB.utils.CustomerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    CustomerMapper customerMapper;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    AddressRepository addressRepository;

    CustomerServiceImpl customerService;

    @BeforeEach
    void setUp(){
        customerService = new CustomerServiceImpl(customerRepository, addressRepository, customerMapper);
    }

    @Test
    @DisplayName("should return empty list")
    public void shouldReturnEmptyList(){
        Mockito.when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        final List<CustomerResponse> customers = customerService.getCustomers();
        assertNotNull(customers);
        assertEquals(new ArrayList<>(), customers);
    }

    @Test
    @DisplayName("should return list with one CustomerResponse")
    public void shouldReturnListWithOneCustomerResponse(){
        //given
        final Customer input = Customer.builder()
                .name("name")
                .lastName("lastName")
                .id(1L)
                .email("mail@mail.com")
                .phoneNo("500600700")
                .serviceRequestSet(Set.of(new ServiceRequest()))
                .vehicleSet(Set.of(Vehicle.builder()
                        .vin("123")
                        .build()))
                .build();

        final CustomerResponse expectedResponse = CustomerResponse.builder()
                .name("name")
                .lastName("lastName")
                .id(1L)
                .email("mail@mail.com")
                .phoneNo("500600700")
                .build();

        Mockito.when(customerRepository.findAll()).thenReturn(List.of(input));
        final List<CustomerResponse> customers = customerService.getCustomers();
        assertNotNull(customers);
        assertEquals(List.of(expectedResponse), customers);
    }
}