package pl.artur.zaczek.car.mechanic.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetCustomer {
    @NotNull
    private long id;
    @NotNull
    private boolean isCompany;
    private String companyName;
    private String companyNip;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    private AddressDTO address;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNo;
    private String secondPhoneNo;
}
