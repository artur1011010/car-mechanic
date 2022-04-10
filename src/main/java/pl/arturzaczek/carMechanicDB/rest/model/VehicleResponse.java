package pl.arturzaczek.carMechanicDB.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.arturzaczek.carMechanicDB.model.BodyType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {

    private long id;
    private String vin;
    private String licensePlate;
    private LocalDate firstRegistrationDate;
    private Integer prodYear;
    private String brand;
    private String model;
    private BodyType bodyType;
    private int mileage;
    private String color;
    private EngineResponse engine;
}
