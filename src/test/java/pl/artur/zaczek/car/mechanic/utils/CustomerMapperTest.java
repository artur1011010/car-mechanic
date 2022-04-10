package pl.artur.zaczek.car.mechanic.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.model.ServiceRequest;
import pl.artur.zaczek.car.mechanic.model.Vehicle;
import pl.artur.zaczek.car.mechanic.rest.model.CreateCustomerRequest;
import pl.artur.zaczek.car.mechanic.rest.model.CustomerResponse;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerMapperTest {

    @Autowired
    CustomerMapper customerMapper;

    @Test
    @DisplayName("customerMapper - should return correct CustomerResponse")
    public void shouldReturnCorrectCustomerResponse() {
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
        //when
        final CustomerResponse actualResponse = customerMapper.customerToResponse(input);
        //then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("customerMapper - should return correct Customer")
    public void shouldReturnCorrectCustomer() {
        //given
        final Customer expectedCustomer = Customer.builder()
                .name("name")
                .lastName("lastName")
                .email("mail@mail.com")
                .phoneNo("500600700")
                .build();

        final CreateCustomerRequest input = CreateCustomerRequest.builder()
                .name("name")
                .lastName("lastName")
                .email("mail@mail.com")
                .phoneNo("500600700")
                .build();
        //when
        final Customer actualResponse = customerMapper.createCustomerRequestToCustomerMapper(input);
        //then
        assertEquals(expectedCustomer, actualResponse);
    }
}