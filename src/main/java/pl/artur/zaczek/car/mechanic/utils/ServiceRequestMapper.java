package pl.artur.zaczek.car.mechanic.utils;

import org.mapstruct.Mapper;
import pl.artur.zaczek.car.mechanic.model.ServiceRequest;
import pl.artur.zaczek.car.mechanic.rest.model.CreateServiceRequest;
import pl.artur.zaczek.car.mechanic.rest.model.ServiceRequestResponse;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface ServiceRequestMapper {
    ServiceRequestResponse toServiceRequestResponse(ServiceRequest source);

    ServiceRequest toServiceRequest(CreateServiceRequest source);

}
