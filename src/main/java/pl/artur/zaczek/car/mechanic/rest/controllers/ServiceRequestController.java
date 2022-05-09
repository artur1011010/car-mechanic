package pl.artur.zaczek.car.mechanic.rest.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.artur.zaczek.car.mechanic.rest.error.ApiErrorResponse;
import pl.artur.zaczek.car.mechanic.rest.model.CreateServiceRequest;
import pl.artur.zaczek.car.mechanic.rest.model.ServiceRequestResponse;
import pl.artur.zaczek.car.mechanic.service.ServiceRequestService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/service-request",
            produces = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return list service requests by customer id or vehicle id",
            notes = "one of two params is required")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ServiceRequestResponse[].class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ApiErrorResponse.class)})
    public ResponseEntity<List<ServiceRequestResponse>> findServiceRequestsByCustomerId(
            @RequestParam(required = false) final Long customerId,
            @RequestParam(required = false) final Long vehicleId) {
        log.info("GET api/service-request with customerId={} and vehicleId={}", customerId, vehicleId);
        return ResponseEntity.ok(serviceRequestService.findSR(Optional.ofNullable(customerId), Optional.ofNullable(vehicleId)));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/service-request/{id}",
            produces = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return service requests with specific id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ServiceRequestResponse[].class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ApiErrorResponse.class)})
    public ResponseEntity<ServiceRequestResponse> getSRById(@PathVariable final Long id) {
        log.info("GET api/service-request/{}", id);
        return ResponseEntity.ok(serviceRequestService.getSRById(id));
    }


    @RequestMapping(
            method = RequestMethod.POST,
            value = "/service-request",
            consumes = "application/json; charset=UTF-8",
            produces = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create new Service Request for specific customerId and vehicleId",
            notes = "Service creates new Service Request, takes body: CreateServiceRequest and 2 query params: userId and vehicleId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Long.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ApiErrorResponse.class)})
    public ResponseEntity<Long> saveNewServiceRequest(@RequestBody @Valid final CreateServiceRequest request, @RequestParam final Long customerId, @RequestParam final Long vehicleId) {
        log.info("POST api/service-request with body={}\ncustomerId={}\nvehicleId={}", request, customerId, vehicleId);
        return ResponseEntity.ok(serviceRequestService.createSR(request, customerId, vehicleId));
    }

}
