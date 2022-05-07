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
import pl.artur.zaczek.car.mechanic.jpa.CustomerRepository;
import pl.artur.zaczek.car.mechanic.jpa.VehicleRepository;
import pl.artur.zaczek.car.mechanic.model.BodyType;
import pl.artur.zaczek.car.mechanic.model.Engine;
import pl.artur.zaczek.car.mechanic.model.Vehicle;
import pl.artur.zaczek.car.mechanic.rest.error.NotFoundException;
import pl.artur.zaczek.car.mechanic.rest.model.EngineResponse;
import pl.artur.zaczek.car.mechanic.rest.model.VehicleResponse;
import pl.artur.zaczek.car.mechanic.utils.VehicleMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}