package pl.artur.zaczek.car.mechanic.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.artur.zaczek.car.mechanic.jpa.AddressRepository;
import pl.artur.zaczek.car.mechanic.jpa.CustomerRepository;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.model.ServiceRequest;
import pl.artur.zaczek.car.mechanic.model.Vehicle;
import pl.artur.zaczek.car.mechanic.rest.error.BadRequestException;
import pl.artur.zaczek.car.mechanic.rest.error.NotFoundException;
import pl.artur.zaczek.car.mechanic.rest.model.CreateCustomer;
import pl.artur.zaczek.car.mechanic.rest.model.CustomerResponse;
import pl.artur.zaczek.car.mechanic.utils.CustomerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, addressRepository, customerMapper);
    }

    @Test
    @DisplayName("should return empty list")
    public void shouldReturnEmptyList() {
        Mockito.when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        final List<CustomerResponse> customers = customerService.getCustomers();
        assertNotNull(customers);
        assertEquals(new ArrayList<>(), customers);
    }

    @Test
    @DisplayName("should return list with one CustomerResponse")
    public void shouldReturnListWithOneCustomerResponse() {
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

    @Test
    @DisplayName("should throw BadRequestException when request is not valid - no nip")
    public void shouldThrowBadRequestExceptionWhenRequestIsNotValidNoNip() {
        final CreateCustomer request = CreateCustomer.builder()
                .isCompany(true)
                .companyName("Comp name")
                .build();
        assertThrows(BadRequestException.class,
                () -> customerService.createUser(request));
    }

    @Test
    @DisplayName("should throw BadRequestException when request is not valid - no comp name")
    public void shouldThrowBadRequestExceptionWhenRequestIsNotValidNoCompName() {
        final CreateCustomer request = CreateCustomer.builder()
                .isCompany(true)
                .companyNip("59949343")
                .build();
        assertThrows(BadRequestException.class,
                () -> customerService.createUser(request));
    }

    @Test
    @DisplayName("should throw BadRequestException when request is not valid - no comp name and nip")
    public void shouldThrowBadRequestExceptionWhenRequestIsNotValidNoCompNameNoNip() {
        final CreateCustomer request = CreateCustomer.builder()
                .isCompany(true)
                .build();
        assertThrows(BadRequestException.class,
                () -> customerService.createUser(request));
    }

    @Test
    @DisplayName("should throw NotFoundException when customer not found")
    public void shouldThrowNotFoundExceptionWhenCustomerNotFound() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> customerService.getCustomerById(1L));
    }

    @Test
    @DisplayName("should return correct response")
    public void shouldReturnCorrectResponse() {
        //given
        final Customer customer = Customer.builder()
                .id(1L)
                .email("abc@mail.com")
                .name("abc")
                .build();
        final CustomerResponse expectedResponse = CustomerResponse.builder()
                .id(1L)
                .email("abc@mail.com")
                .name("abc")
                .build();
        //when
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        final CustomerResponse actualResponse = customerService.getCustomerById(1L);
        //then
        assertEquals(expectedResponse, actualResponse);

    }

}