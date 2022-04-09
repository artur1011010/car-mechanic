package pl.arturzaczek.carMechanicDB.rest.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.arturzaczek.carMechanicDB.rest.error.ApiErrorResponse;
import pl.arturzaczek.carMechanicDB.rest.model.ServiceRequestResponse;
import pl.arturzaczek.carMechanicDB.service.ServiceRequestService;

import java.util.List;

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
    @ApiOperation(value = "return list service requests by user id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ServiceRequestResponse[].class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ApiErrorResponse.class)})
    public ResponseEntity<List<ServiceRequestResponse>> findServiceRequestsByUserId(@RequestParam Long userId){
        return ResponseEntity.ok(serviceRequestService.findSRByCustomerId(userId));
    }

}
