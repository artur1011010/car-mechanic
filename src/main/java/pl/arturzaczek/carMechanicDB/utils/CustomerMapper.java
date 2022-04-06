package pl.arturzaczek.carMechanicDB.utils;

import org.mapstruct.Mapper;
import pl.arturzaczek.carMechanicDB.model.Customer;
import pl.arturzaczek.carMechanicDB.rest.model.CreateCustomerRequest;
import pl.arturzaczek.carMechanicDB.rest.model.CustomerResponse;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse customerToResponse(Customer source);

    Customer createCustomerRequestToCustomerMapper(CreateCustomerRequest source);
}
