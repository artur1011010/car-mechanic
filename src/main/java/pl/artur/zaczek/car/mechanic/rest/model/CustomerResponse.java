package pl.artur.zaczek.car.mechanic.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.artur.zaczek.car.mechanic.model.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private long id;
    private boolean isCompany;
    private String companyName;
    private String companyNip;
    private String name;
    private String lastName;
    private Address address;
    private String email;
    private String phoneNo;
    private String secondPhoneNo;
}
