package pl.artur.zaczek.car.mechanic.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.artur.zaczek.car.mechanic.jpa.AddressRepository;
import pl.artur.zaczek.car.mechanic.jpa.CustomerRepository;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.model.ServiceRequest;
import pl.artur.zaczek.car.mechanic.model.Vehicle;
import pl.artur.zaczek.car.mechanic.rest.error.BadRequestException;
import pl.artur.zaczek.car.mechanic.rest.error.NotFoundException;
import pl.artur.zaczek.car.mechanic.rest.model.AddressDTO;
import pl.artur.zaczek.car.mechanic.rest.model.CreateCustomer;
import pl.artur.zaczek.car.mechanic.rest.model.CustomerResponse;
import pl.artur.zaczek.car.mechanic.rest.model.SetCustomer;
import pl.artur.zaczek.car.mechanic.utils.CustomerMapper;
import pl.artur.zaczek.car.mechanic.utils.ModelValidator;

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

    @Autowired
    ModelValidator modelValidator;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    AddressRepository addressRepository;

    CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, addressRepository, customerMapper, modelValidator);
    }

    /***********  get customer  ***********/

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
                () -> customerService.createCustomer(request));
    }

    @Test
    @DisplayName("should throw BadRequestException when request is not valid - no comp name")
    public void shouldThrowBadRequestExceptionWhenRequestIsNotValidNoCompName() {
        final CreateCustomer request = CreateCustomer.builder()
                .isCompany(true)
                .companyNip("59949343")
                .build();
        assertThrows(BadRequestException.class,
                () -> customerService.createCustomer(request));
    }

    @Test
    @DisplayName("should throw BadRequestException when request is not valid - no comp name and nip")
    public void shouldThrowBadRequestExceptionWhenRequestIsNotValidNoCompNameNoNip() {
        final CreateCustomer request = CreateCustomer.builder()
                .isCompany(true)
                .build();
        assertThrows(BadRequestException.class,
                () -> customerService.createCustomer(request));
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

    /***********  create customer  ***********/

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    @DisplayName("should throw BadRequestException when is company and nip is empty or blank in CreateCustomer")
    public void shouldThrowBadRequestExceptionWhenIsCompanyAndNipIsEmptyOrBlankInCreateCustomer(String input) {
        //given
        final CreateCustomer customer = CreateCustomer.builder()
                .email("abc@mail.com")
                .name("abc")
                .isCompany(true)
                .companyName(input)
                .phoneNo("1515155")
                .address(AddressDTO.builder()
                        .city("Warsaw")
                        .flatNo(7)
                        .streetNo(44)
                        .street("Ordynacka")
                        .build())
                .build();
        //then
        final BadRequestException exception = assertThrows(BadRequestException.class, () -> customerService.createCustomer(customer));
        assertEquals(HttpStatus.BAD_REQUEST.toString(), exception.getCode());
        assertEquals("Customer type company requires companyNip and companyName", exception.getMessage());
    }

    @Test
    @DisplayName("should throw BadRequestException when customer request is null")
    public void shouldCreateCustomer() {
        final BadRequestException exception = assertThrows(BadRequestException.class, () -> customerService.createCustomer(null));
        assertEquals(HttpStatus.BAD_REQUEST.toString(), exception.getCode());
        assertEquals("Customer request can not be null", exception.getMessage());
    }

    @Test
    @DisplayName("should create new customer")
    public void shouldCreateNewCustomer() {
        //given
        final CreateCustomer customer = CreateCustomer.builder()
                .email("abc@mail.com")
                .name("abc")
                .isCompany(false)
                .phoneNo("1515155")
                .address(AddressDTO.builder()
                        .city("Warsaw")
                        .flatNo(7)
                        .streetNo(44)
                        .street("Ordynacka")
                        .build())
                .build();
        //when
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(Customer.builder().id(1L).build());
        //then
        final Long actualResponse = customerService.createCustomer(customer);
        assertEquals(1L, actualResponse);
    }

    /***********  set customer  ***********/
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    @DisplayName("should throw BadRequestException when is company and nip is empty or blank in SetCustomer")
    public void shouldThrowBadRequestExceptionWhenIsCompanyAndNipIsEmptyOrBlankInSetCustomer(String input) {
        //given
        final SetCustomer customer = SetCustomer.builder()
                .email("abc@mail.com")
                .name("abc")
                .isCompany(true)
                .companyName(input)
                .phoneNo("1515155")
                .address(AddressDTO.builder()
                        .city("Warsaw")
                        .flatNo(7)
                        .streetNo(44)
                        .street("Ordynacka")
                        .build())
                .build();
        //then
        final BadRequestException exception = assertThrows(BadRequestException.class, () -> customerService.setCustomer(customer));
        assertEquals(HttpStatus.BAD_REQUEST.toString(), exception.getCode());
        assertEquals("Customer type company requires companyNip and companyName", exception.getMessage());
    }

    @Test
    @DisplayName("should throw BadRequestException when customer request is null")
    public void shouldCreateCustomerInSetCustomer() {
        final BadRequestException exception = assertThrows(BadRequestException.class, () -> customerService.setCustomer(null));
        assertEquals(HttpStatus.BAD_REQUEST.toString(), exception.getCode());
        assertEquals("Customer request can not be null", exception.getMessage());
    }

    @Test
    @DisplayName("should set customer")
    public void shouldSetCustomer() {
        //given
        final SetCustomer customer = SetCustomer.builder()
                .id(1L)
                .email("abc@mail.com")
                .name("abc")
                .isCompany(false)
                .phoneNo("1515155")
                .address(AddressDTO.builder()
                        .city("Warsaw")
                        .flatNo(7)
                        .streetNo(44)
                        .street("Ordynacka")
                        .build())
                .build();
        //when
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(Customer.builder().id(1L).build()));
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(Customer.builder().id(1L).build());
        //then
        assertDoesNotThrow(()->customerService.setCustomer(customer));
    }
}