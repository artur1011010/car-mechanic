package pl.artur.zaczek.car.mechanic.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.artur.zaczek.car.mechanic.model.Fuel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EngineResponse {

    private long id;
    private double power;
    private double capacity;
    private Fuel fuel;
}
