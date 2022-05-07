package pl.artur.zaczek.car.mechanic.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Valid
public class CreateServiceRequest {
    @NotBlank
    private String title;
    private String comment;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime finishTime;
    @NotNull
    private boolean isDone;
    private BigDecimal price;
    private BigDecimal discount;
}
