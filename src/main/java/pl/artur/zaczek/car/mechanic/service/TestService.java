package pl.artur.zaczek.car.mechanic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.artur.zaczek.car.mechanic.jpa.*;
import pl.artur.zaczek.car.mechanic.model.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
                .isCompany(false)
                .email("artur@gmail.com")
                .lastName("Zaczek")
                .name("Artur")
                .address(address)
                .build();

        Customer customer2 = Customer
                .builder()
                .isCompany(false)
                .email("piotr@gmail.com")
                .lastName("Chłopicki")
                .name("Piotr")
                .address(address3)
                .build();

        Customer customer3 = Customer
                .builder()
                .isCompany(true)
                .email("marcin@gmail.com")
                .lastName("Sabaturski")
                .name("Marcin")
                .companyName("Sabaturski company")
                .companyNip("7292660020")
                .address(address2)
                .build();

        Customer customer4 = Customer
                .builder()
                .isCompany(true)
                .email("witalij@gmail.com")
                .lastName("Sudnik")
                .name("Witalij")
                .phoneNo("700600500")
                .companyName("Witalij company")
                .companyNip("7292660000")
                .address(address2)
                .build();

        Customer customer5 = Customer
                .builder()
                .isCompany(false)
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
                .startTime(LocalDateTime.now())
                .finishTime(LocalDateTime.of(2022, 5, 10, 12, 0))
                .isDone(true)
                .price(BigDecimal.valueOf(1500L))
                .vehicle(vehicle)
                .build();

        serviceRequestRepository.saveAndFlush(sr);

    }
}
