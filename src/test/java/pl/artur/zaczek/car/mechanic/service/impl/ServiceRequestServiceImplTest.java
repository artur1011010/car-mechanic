package pl.artur.zaczek.car.mechanic.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.artur.zaczek.car.mechanic.jpa.CustomerRepository;
import pl.artur.zaczek.car.mechanic.jpa.ServiceRequestRepository;
import pl.artur.zaczek.car.mechanic.jpa.VehicleRepository;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.model.ServiceRequest;
import pl.artur.zaczek.car.mechanic.model.Vehicle;
import pl.artur.zaczek.car.mechanic.rest.error.NotFoundException;
import pl.artur.zaczek.car.mechanic.rest.model.CreateServiceRequest;
import pl.artur.zaczek.car.mechanic.rest.model.ServiceRequestResponse;
import pl.artur.zaczek.car.mechanic.utils.ServiceRequestMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ServiceRequestServiceImplTest {


    @Autowired
    ServiceRequestMapper serviceRequestMapper;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    ServiceRequestRepository serviceRequestRepository;

    @Mock
    VehicleRepository vehicleRepository;


    ServiceRequestServiceImpl serviceRequestService;

    @BeforeEach
    void setUp() {
        serviceRequestService = new ServiceRequestServiceImpl(serviceRequestRepository ,customerRepository,vehicleRepository, serviceRequestMapper);
    }

    @Test
    @DisplayName("should throw NotFoundException when customerId is not found")
    public void shouldThrowNotFoundExceptionWhenCustomerIdIsNotFound() {
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> serviceRequestService.findSRByCustomerId(123L));
    }

    @Test
    @DisplayName("should return SR when customerId is found in DB")
    public void shouldReturnSRWhenCustomerIdFoundInDB() {
        //given
        final ServiceRequest serviceRequest = ServiceRequest.builder()
                .id(1L)
                .comment("test")
                .isDone(false)
                .title("Test")
                .build();

        final ServiceRequestResponse expectedResponse = ServiceRequestResponse.builder()
                .id(1L)
                .comment("test")
                .isDone(false)
                .title("Test")
                .build();
        //when
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Customer.builder().build()));
        Mockito.when(serviceRequestRepository.findServiceRequestByCustomer(Mockito.any())).thenReturn(List.of(serviceRequest));
        //then
        assertEquals(List.of(expectedResponse), serviceRequestService.findSRByCustomerId(1L));
    }

    @Test
    @DisplayName("should return empty list when customerId is found in DB but there is no SR on DB")
    public void shouldReturnEmptyListWhenCustomerIdFoundInDBButThereIsNoSR() {
        //when
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Customer.builder().build()));
        Mockito.when(serviceRequestRepository.findServiceRequestByCustomer(Mockito.any())).thenReturn(new ArrayList<>());
        //then
        assertEquals(new ArrayList<>(), serviceRequestService.findSRByCustomerId(1L));
    }

    @Test
    @DisplayName("should throw NotFoundException when vehicleId is not found")
    public void shouldThrowNotFoundExceptionWhenCVehicleIdIsNotFound() {
        Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> serviceRequestService.findSRByVehicleId(123L));
    }

    @Test
    @DisplayName("should return SR when vehicle is found in DB")
    public void shouldReturnSRWhenVehicleIdFoundInDB() {
        //given
        final ServiceRequest serviceRequest = ServiceRequest.builder()
                .id(1L)
                .comment("test")
                .isDone(false)
                .title("Test")
                .build();

        final ServiceRequestResponse expectedResponse = ServiceRequestResponse.builder()
                .id(1L)
                .comment("test")
                .isDone(false)
                .title("Test")
                .build();
        //when
        Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Vehicle.builder().build()));
        Mockito.when(serviceRequestRepository.findServiceRequestsByVehicle(Mockito.any())).thenReturn(List.of(serviceRequest));
        //then
        assertEquals(List.of(expectedResponse), serviceRequestService.findSRByVehicleId(1L));
    }

    @Test
    @DisplayName("should return empty list when vehicleId is found in DB but there is no SR on DB")
    public void shouldReturnEmptyListWhenVehicleIdFoundInDBButThereIsNoSR() {
        //when
        Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Vehicle.builder().build()));
        Mockito.when(serviceRequestRepository.findServiceRequestsByVehicle(Mockito.any())).thenReturn(new ArrayList<>());
        //then
        assertEquals(new ArrayList<>(), serviceRequestService.findSRByVehicleId(1L));
    }

    @Test
    @DisplayName("should save new SR on DB")
    public void shouldSaveNewSRonDB() {
        //given
        final CreateServiceRequest createServiceRequest = CreateServiceRequest.builder()
                .comment("test")
                .isDone(false)
                .title("Test")
                .build();
        //when
        Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Vehicle.builder().build()));
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Customer.builder().build()));
        ServiceRequestMapper serviceRequestMapper1 = new ServiceRequestMapper() {
            @Override
            public ServiceRequestResponse toServiceRequestResponse(ServiceRequest source) {
                return null;
            }

            @Override
            public ServiceRequest toServiceRequest(CreateServiceRequest source) {
                return ServiceRequest.builder()
                        .id(1L)
                        .comment("test")
                        .isDone(false)
                        .title("Test")
                        .build();
            }
        };
        ServiceRequestServiceImpl serviceRequestService1 = new ServiceRequestServiceImpl(serviceRequestRepository ,customerRepository,vehicleRepository, serviceRequestMapper1);
        //then
        assertEquals(1L, serviceRequestService1.createSR(createServiceRequest, 1L, 1L));
    }

    @Test
    @DisplayName("should throw NotFoundException when customer is not found")
    public void shouldThrowNotFoundExceptionWhenCustomerIsNotFound() {
        //given
        final CreateServiceRequest createServiceRequest = CreateServiceRequest.builder()
                .comment("test")
                .isDone(false)
                .title("Test")
                .build();
        //when
        Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Vehicle.builder().build()));
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        final NotFoundException exception = assertThrows(NotFoundException.class,
                () -> serviceRequestService.createSR(createServiceRequest, 1L, 1L));
        assertEquals(HttpStatus.NOT_FOUND.name(), exception.getCode());
        assertEquals("Customer with id: 1 not found", exception.getMessage());
    }

    @Test
    @DisplayName("should throw NotFoundException when vehicle is not found")
    public void shouldThrowNotFoundExceptionWhenVehicleIsNotFound() {
        //given
        final CreateServiceRequest createServiceRequest = CreateServiceRequest.builder()
                .comment("test")
                .isDone(false)
                .title("Test")
                .build();

        //when
        Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Customer.builder().build()));
        final NotFoundException exception = assertThrows(NotFoundException.class,
                () -> serviceRequestService.createSR(createServiceRequest, 1L, 1L));
        assertEquals(HttpStatus.NOT_FOUND.name(), exception.getCode());
        assertEquals("Vehicle with id: 1 not found", exception.getMessage());
    }


}