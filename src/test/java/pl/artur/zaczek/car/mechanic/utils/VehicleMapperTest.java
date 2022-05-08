package pl.artur.zaczek.car.mechanic.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.artur.zaczek.car.mechanic.model.BodyType;
import pl.artur.zaczek.car.mechanic.model.Engine;
import pl.artur.zaczek.car.mechanic.model.Fuel;
import pl.artur.zaczek.car.mechanic.model.Vehicle;
import pl.artur.zaczek.car.mechanic.rest.model.EngineResponse;
import pl.artur.zaczek.car.mechanic.rest.model.VehicleResponse;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class VehicleMapperTest {

    @Autowired
    VehicleMapper vehicleMapper;

    @Test
    @DisplayName("VehicleMapper - should return correct VehicleRequest")
    public void shouldReturnCorrectVehicleResponse() {
        //given
        final Vehicle input = Vehicle.builder()
              .brand("red")
                .bodyType(BodyType.COMBI)
                .prodYear(2014)
                .brand("opel")
                .mileage(166000)
                .firstRegistrationDate(LocalDate.now())
                .licensePlate("EL 756RC")
                .engine(Engine.builder()
                        .capacity(150.95)
                        .power(100)
                        .fuel(Fuel.PETROL)
                        .build())
                .build();

        final VehicleResponse expectedResponse = VehicleResponse.builder()
                .brand("red")
                .bodyType(BodyType.COMBI)
                .prodYear(2014)
                .brand("opel")
                .mileage(166000)
                .firstRegistrationDate(LocalDate.now())
                .licensePlate("EL 756RC")
                .engine(EngineResponse.builder()
                        .capacity(150.95)
                        .power(100)
                        .fuel(Fuel.PETROL)
                        .build())
                .build();
        //when
        final VehicleResponse actualResponse = vehicleMapper.vehicleToResponse(input);
        //then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("VehicleMapper - should return correct VehicleRequest with null engine")
    public void shouldReturnCorrectVehicleResponseWithNullEngine() {
        //given
        final Vehicle input = Vehicle.builder()
                .brand("red")
                .bodyType(BodyType.COMBI)
                .prodYear(2014)
                .brand("opel")
                .mileage(166000)
                .firstRegistrationDate(LocalDate.now())
                .licensePlate("EL 756RC")
                .build();

        final VehicleResponse expectedResponse = VehicleResponse.builder()
                .brand("red")
                .bodyType(BodyType.COMBI)
                .prodYear(2014)
                .brand("opel")
                .mileage(166000)
                .firstRegistrationDate(LocalDate.now())
                .licensePlate("EL 756RC")
                .build();
        //when
        final VehicleResponse actualResponse = vehicleMapper.vehicleToResponse(input);
        //then
        assertEquals(expectedResponse, actualResponse);
    }

}