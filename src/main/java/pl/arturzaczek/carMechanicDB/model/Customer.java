package pl.arturzaczek.carMechanicDB.model;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean isCompany;
    private String companyName;
    private String companyNip;
    private String name;
    private String lastName;
    @OneToOne(cascade = CascadeType.MERGE)
    private Address address;
    private String email;
    private String phoneNo;
    private String secondPhoneNo;
    @ManyToMany
    @JoinTable(name = "CUSTOMER_VEHICLE", joinColumns = {@JoinColumn(name = "CUSTOMER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "VEHICLE_ID")})
    private Set<Vehicle> vehicleSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<ServiceRequest> serviceRequestSet;

}
