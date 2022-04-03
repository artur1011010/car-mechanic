package pl.arturzaczek.carMechanicDB.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.arturzaczek.carMechanicDB.jpa.*;
import pl.arturzaczek.carMechanicDB.model.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {
    private final AddressRepo addressRepo;
    private final CustomerRepo customerRepo;
    private final EngineRepo engineRepo;
    private final SRRepo srRepo;
    private final VehicleRepo vehicleRepo;

    @PostConstruct
    public void test(){
        Engine engine = Engine
                .builder()
                .capacity(1700)
                .fuel(Fuel.PETROL)
                .power(130)
                .build();

        Vehicle vehicle = Vehicle
                .builder()
                .brand("Mercedes")
                .model("A150")
                .firstRegistrationDate(LocalDate.now())
                .vin("VIN")
                .licensePlate("EL2000")
                .engine(engine)
                .build();

        Address address = Address
                .builder()
                .city("Lodz")
                .flatNo(5)
                .postalCode("93-223")
                .street("Kutrowa")
                .build();

        Customer customer = Customer
                .builder()
                .email("aasa@gmail.com")
                .lastName("Zaczek")
                .name("Artur")
                .address(address)
                .build();

        engineRepo.saveAndFlush(engine);
        vehicleRepo.saveAndFlush(vehicle);
        addressRepo.saveAndFlush(address);
        customerRepo.saveAndFlush(customer);



        final ServiceRequest sr = ServiceRequest.builder()
                .comment("pierwszy sr")
                .title("pierwszy sr")
                .isDone(true)
                .price(BigDecimal.valueOf(1500L))
                .vehicle(vehicle)
                .build();

        srRepo.saveAndFlush(sr);

    }
}
