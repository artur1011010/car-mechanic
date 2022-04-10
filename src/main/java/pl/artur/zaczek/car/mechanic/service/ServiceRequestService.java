package pl.artur.zaczek.car.mechanic.service;

import pl.artur.zaczek.car.mechanic.rest.model.CreateServiceRequest;
import pl.artur.zaczek.car.mechanic.rest.model.ServiceRequestResponse;

import java.util.List;

public interface ServiceRequestService {
    List<ServiceRequestResponse> findSRByCustomerId(Long id);

    Long createSR(CreateServiceRequest request, Long userId, Long vehicleId);
}
