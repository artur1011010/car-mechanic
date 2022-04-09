package pl.arturzaczek.carMechanicDB.service;

import pl.arturzaczek.carMechanicDB.rest.model.CreateServiceRequest;
import pl.arturzaczek.carMechanicDB.rest.model.ServiceRequestResponse;

import java.util.List;

public interface ServiceRequestService {
    List<ServiceRequestResponse> findSRByCustomerId(Long id);

    Long createSR(CreateServiceRequest request, Long userId, Long vehicleId);
}
