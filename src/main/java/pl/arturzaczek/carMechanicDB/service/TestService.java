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
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final EngineRepository engineRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final VehicleRepository vehicleRepository;

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
                .streetNo(6)
                .city("Lodz")
                .street("Kutrowa")
                .postalCode("93-223")
                .build();

        Address address2 = Address
                .builder()
                .streetNo(227)
                .flatNo(8)
                .city("Łódź")
                .street("Kilińskiego")
                .postalCode("93-122")
                .build();

        Address address3 = Address
                .builder()
                .streetNo(44)
                .flatNo(7)
                .city("Łódź")
                .street("Radwańska")
                .postalCode("93-003")
                .build();

        Customer customer1 = Customer
                .builder()
                .email("artur@gmail.com")
                .lastName("Zaczek")
                .name("Artur")
                .address(address)
                .build();

        Customer customer2 = Customer
                .builder()
                .email("piotr@gmail.com")
                .lastName("Chłopicki")
                .name("Piotr")
                .address(address3)
                .build();

        Customer customer3 = Customer
                .builder()
                .email("marcin@gmail.com")
                .lastName("Sabaturski")
                .name("Marcin")
                .address(address2)
                .build();

        Customer customer4 = Customer
                .builder()
                .email("witalij@gmail.com")
                .lastName("Sudnik")
                .name("Witalij")
                .address(address2)
                .build();

        Customer customer5 = Customer
                .builder()
                .email("grzegorz@gmail.com")
                .lastName("Trojanowski")
                .name("Grzegorz")
                .address(address3)
                .build();

        Customer customer6 = Customer
                .builder()
                .email("mateusz@gmail.com")
                .lastName("Sysio")
                .name("Mateusz")
                .address(address3)
                .build();


        engineRepository.saveAndFlush(engine);
        vehicleRepository.saveAndFlush(vehicle);
        addressRepository.saveAndFlush(address);
        addressRepository.saveAndFlush(address3);
        addressRepository.saveAndFlush(address2);
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);
        customerRepository.save(customer5);
        customerRepository.save(customer6);

        final ServiceRequest sr = ServiceRequest.builder()
                .customer(customer1)
                .comment("pierwszy sr")
                .title("pierwszy sr")
                .isDone(true)
                .price(BigDecimal.valueOf(1500L))
                .vehicle(vehicle)
                .build();

        serviceRequestRepository.saveAndFlush(sr);

    }
}
