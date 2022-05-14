package pl.artur.zaczek.car.mechanic.service;

import pl.artur.zaczek.car.mechanic.rest.model.CreateServiceRequest;
import pl.artur.zaczek.car.mechanic.rest.model.ServiceRequestResponse;
import pl.artur.zaczek.car.mechanic.rest.model.SetServiceRequest;

import java.util.List;
import java.util.Optional;

public interface ServiceRequestService {
    List<ServiceRequestResponse> findSRByCustomerId(Long customerId);
    List<ServiceRequestResponse> findSRByVehicleId(Long vehicleId);
    List<ServiceRequestResponse> findSR(Optional<Long> vehicleId, Optional<Long> customerId);
    Long createSR(CreateServiceRequest request, Long customerId, Long vehicleId);
    ServiceRequestResponse getSRById(Long id);
    void setSR(SetServiceRequest request);
}
