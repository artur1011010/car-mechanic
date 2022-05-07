package pl.artur.zaczek.car.mechanic.utils;

import org.mapstruct.Mapper;
import pl.artur.zaczek.car.mechanic.model.Customer;
import pl.artur.zaczek.car.mechanic.rest.model.CreateCustomer;
import pl.artur.zaczek.car.mechanic.rest.model.CustomerResponse;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface CustomerMapper {
    CustomerResponse customerToResponse(Customer source);

    Customer createCustomerRequestToCustomerMapper(CreateCustomer source);
}
