package pl.arturzaczek.carMechanicDB.utils;

import org.mapstruct.Mapper;
import pl.arturzaczek.carMechanicDB.model.ServiceRequest;
import pl.arturzaczek.carMechanicDB.rest.model.CreateServiceRequest;
import pl.arturzaczek.carMechanicDB.rest.model.ServiceRequestResponse;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface ServiceRequestMapper {
    ServiceRequestResponse ToServiceRequestResponse(ServiceRequest source);
    ServiceRequest ToServiceRequest(CreateServiceRequest source);

}
