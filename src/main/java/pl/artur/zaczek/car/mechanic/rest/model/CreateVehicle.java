package pl.artur.zaczek.car.mechanic.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.artur.zaczek.car.mechanic.model.BodyType;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Valid
public class CreateVehicle {
    @NotBlank
    private String vin;
    @NotBlank
    private String licensePlate;
    @NotNull
    private LocalDate firstRegistrationDate;
    private Integer prodYear;
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    private BodyType bodyType;
    private int mileage;
    @NotBlank
    private String color;
    @NotNull
    private EngineDTO engine;

}
