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
import pl.artur.zaczek.car.mechanic.jpa.VehicleRepository;
import pl.artur.zaczek.car.mechanic.model.BodyType;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.model.Engine;
import pl.artur.zaczek.car.mechanic.model.Vehicle;
import pl.artur.zaczek.car.mechanic.rest.error.NotFoundException;
import pl.artur.zaczek.car.mechanic.rest.model.CreateVehicle;
import pl.artur.zaczek.car.mechanic.rest.model.EngineResponse;
import pl.artur.zaczek.car.mechanic.rest.model.SetVehicle;
import pl.artur.zaczek.car.mechanic.rest.model.VehicleResponse;
import pl.artur.zaczek.car.mechanic.utils.VehicleMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class VehicleServiceImplTest {

    @Autowired
    VehicleMapper vehicleMapper;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    VehicleRepository vehicleRepository;

    VehicleServiceImpl vehicleService;

    @BeforeEach
    void setUp() {
        vehicleService = new VehicleServiceImpl(vehicleRepository, customerRepository, vehicleMapper);
    }

    /***********  get vehicle  ***********/

    @Test
    @DisplayName("should return correct response")
    public void shouldReturnCorrectResponse() {
        //given
        final Vehicle vehicle = Vehicle.builder()
                .bodyType(BodyType.COMBI)
                .id(1L)
                .color("RED")
                .brand("Mercedes")
                .engine(Engine.builder()
                        .id(12L)
                        .power(150.0)
                        .capacity(1500.2)
                        .build())
                .build();

        final VehicleResponse expectedResponse = VehicleResponse.builder()
                .bodyType(BodyType.COMBI)
                .id(1L)
                .color("RED")
                .brand("Mercedes")
                .engine(EngineResponse.builder()
                        .id(12L)
                        .power(150.0)
                        .capacity(1500.2)
                        .build())
                .build();
        //when
        Mockito.when(vehicleRepository.findAll()).thenReturn(List.of(vehicle));
        //then
        final List<VehicleResponse> actualResponse = vehicleService.getVehicles(Optional.empty());
        assertEquals(List.of(expectedResponse), actualResponse);
    }

    @Test
    @DisplayName("should return correct response when customerId is present")
    public void shouldReturnCorrectResponseWhenCustomerIdIsPresent() {
        //given
        final Vehicle vehicle = Vehicle.builder()
                .bodyType(BodyType.COMBI)
                .id(1L)
                .color("RED")
                .brand("Mercedes")
                .engine(Engine.builder()
                        .id(12L)
                        .power(150.0)
                        .capacity(1500.2)
                        .build())
                .build();

        final VehicleResponse expectedResponse = VehicleResponse.builder()
                .bodyType(BodyType.COMBI)
                .id(1L)
                .color("RED")
                .brand("Mercedes")
                .engine(EngineResponse.builder()
                        .id(12L)
                        .power(150.0)
                        .capacity(1500.2)
                        .build())
                .build();
        //when
        Mockito.when(vehicleRepository.findAll()).thenReturn(List.of(vehicle));
        Mockito.when(customerRepository.findById(123L)).thenReturn(Optional.of(Customer
                .builder()
                .vehicleSet(Set.of(vehicle))
                .build()));
        final List<VehicleResponse> actualResponse = vehicleService.getVehicles(Optional.of(123L));
        //then
        assertEquals(List.of(expectedResponse), actualResponse);
    }

    @Test
    @DisplayName("should throw NotFoundException when CustomerId is not found")
    public void shouldThrowNotFoundExceptionWhenCustomerIdIsNotFound() {
        //when
        Mockito.when(customerRepository.findById(123L)).thenReturn(Optional.empty());
        //then
        final NotFoundException exception = assertThrows(NotFoundException.class,
                () -> vehicleService.getVehicles(Optional.of(123L)));
        assertEquals(HttpStatus.NOT_FOUND.name(), exception.getCode());
        assertEquals("Customer with id: 123 not found", exception.getMessage());
    }

    @Test
    @DisplayName("should return empty array")
    public void shouldReturnEmptyArray() {
        //when
        Mockito.when(vehicleRepository.findAll()).thenReturn(Collections.emptyList());
        //then
        final List<VehicleResponse> actualResponse = vehicleService.getVehicles(Optional.empty());
        assertEquals(new ArrayList<>(), actualResponse);
    }

    @Test
    @DisplayName("should return correct Vehicle response when getVehicleById is called")
    public void shouldReturnCorrectVehicleResponseWhenGetVehicleByIdIsCalled() {
        //given
        final Vehicle vehicle = Vehicle.builder()
                .bodyType(BodyType.COMBI)
                .id(1L)
                .color("RED")
                .brand("Mercedes")
                .engine(Engine.builder()
                        .id(12L)
                        .power(150.0)
                        .capacity(1500.2)
                        .build())
                .build();

        final VehicleResponse expectedResponse = VehicleResponse.builder()
                .bodyType(BodyType.COMBI)
                .id(1L)
                .color("RED")
                .brand("Mercedes")
                .engine(EngineResponse.builder()
                        .id(12L)
                        .power(150.0)
                        .capacity(1500.2)
                        .build())
                .build();

        Mockito.when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        assertEquals(expectedResponse, vehicleService.getVehicleById(1L));
    }

    @Test
    @DisplayName("should throw NotFoundException when Vehicle not found")
    public void shouldThrowNotFoundExceptionWhenVehicleNotFound() {
        Mockito.when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> vehicleService.getVehicleById(1L));
    }

    /***********  create vehicle  ***********/

    @Test
    @DisplayName("should throw NotFoundException when customer is not found")
    public void shouldThrowNotFoundExceptionWhenCustomerIsNotFound() {
        //given
        final CreateVehicle request = CreateVehicle.builder()
                .brand("test")
                .color("red")
                .vin("12133")
                .build();
        //when
        Mockito.when(customerRepository.findById(123L)).thenReturn(Optional.empty());
        final NotFoundException exception = assertThrows(NotFoundException.class,
                () -> vehicleService.createVehicle(request, 123L));
        assertEquals(HttpStatus.NOT_FOUND.name(), exception.getCode());
        assertEquals("Customer with id: 123 not found", exception.getMessage());
    }

    @Test
    @DisplayName("should create new vehicle")
    public void shouldCreateNewVehicle() {
        //given
        final CreateVehicle request = CreateVehicle.builder()
                .brand("test")
                .color("red")
                .vin("12133")
                .build();
        //when
        Mockito.when(customerRepository.findById(123L)).thenReturn(Optional.of(Customer.builder().vehicleSet(new HashSet<>()).build()));
        Mockito.when(vehicleRepository.save(Mockito.any())).thenReturn(Vehicle.builder().id(1L).build());
        //then
        final Long actualResponse = vehicleService.createVehicle(request, 123L);
        assertEquals(1L, actualResponse);
    }

    /***********  set vehicle  ***********/

    @Test
    @DisplayName("should throw NotFoundException when vehicle is not found in set vehicle")
    public void shouldThrowNotFoundExceptionWhenCustomerIsNotFoundInSetVehicle() {
        //given
        final SetVehicle request = SetVehicle.builder()
                .id(1L)
                .brand("test")
                .color("red")
                .vin("12133")
                .build();
        //when
        Mockito.when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());
        final NotFoundException exception = assertThrows(NotFoundException.class,
                () -> vehicleService.setVehicle(request));
        assertEquals(HttpStatus.NOT_FOUND.name(), exception.getCode());
        assertEquals("Vehicle with id: 1 not found", exception.getMessage());
    }

    @Test
    @DisplayName("should set vehicle")
    public void shouldSetVehicle() {
        //given
        final SetVehicle request = SetVehicle.builder()
                .id(1L)
                .brand("test")
                .color("red")
                .vin("12133")
                .build();
        //when
        Mockito.when(vehicleRepository.findById(1L)).thenReturn(Optional.of(Vehicle.builder().build()));
        Mockito.when(vehicleRepository.save(Mockito.any())).thenReturn(Vehicle.builder().id(1L).build());
        //then
        assertDoesNotThrow(() -> vehicleService.setVehicle(request));
    }
}