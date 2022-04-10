package pl.artur.zaczek.car.mechanic.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double power;
    private double capacity;
    @Enumerated(value = EnumType.STRING)
    private Fuel fuel;
}
