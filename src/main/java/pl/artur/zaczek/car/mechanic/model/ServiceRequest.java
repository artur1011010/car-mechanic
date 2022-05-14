package pl.artur.zaczek.car.mechanic.model;

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
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    private String createdUser;
    private String modifiedUser;
    private boolean isDone;
    private BigDecimal price;
    private BigDecimal discount;
    @ManyToOne
    private Vehicle vehicle;
    @ManyToOne @JoinColumn(name = "customer_id")
    private Customer customer;



}
