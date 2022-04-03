package pl.arturzaczek.carMechanicDB.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String comment;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private boolean isDone;
    private BigDecimal price;
    private double discount;
    @ManyToOne
    private Vehicle vehicle;
}
