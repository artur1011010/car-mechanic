package pl.artur.zaczek.car.mechanic.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private String street;
    private String city;
    private String postalCode;
    private Integer streetNo;
    private Integer flatNo;
}
