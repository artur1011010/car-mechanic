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
import pl.artur.zaczek.car.mechanic.rest.model.CreateVehicle;
import pl.artur.zaczek.car.mechanic.rest.model.SetVehicle;
import pl.artur.zaczek.car.mechanic.rest.model.VehicleResponse;
import pl.artur.zaczek.car.mechanic.service.VehicleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class VehicleController {

    private final VehicleService vehicleService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/vehicle",
            produces = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return list of all vehicles, optional path param customerId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = VehicleResponse[].class),
            @ApiResponse(code = 404, message = "Not found", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ApiErrorResponse.class)})
    public ResponseEntity<List<VehicleResponse>> getVehicles(@RequestParam(required = false) final Long customerId) {
        log.info("GET api/vehicle with optional customerId: {}", customerId );
        return ResponseEntity.ok(vehicleService.getVehicles(Optional.ofNullable(customerId)));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/vehicle/{id}",
            produces = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return vehicle with specific id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = VehicleResponse[].class),
            @ApiResponse(code = 404, message = "Not found", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ApiErrorResponse.class)})
    public ResponseEntity<VehicleResponse> getVehicle(@PathVariable final Long id) {
        log.info("GET api/vehicle/{}", id);
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/vehicle",
            produces = "application/json; charset=UTF-8",
            consumes = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create new Vehicle and link it customerId",
            notes = "Creates a new Vehicle and return created id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - Vehicle successfully created with id:", response = Long.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ApiErrorResponse.class)})
    public ResponseEntity<Long> createVehicle(@RequestBody @Valid final CreateVehicle request, @RequestParam final Long customerId) {
        log.info("POST api/vehicle with body: {} \ncustomerId: {}", request, customerId);
        return ResponseEntity.ok(vehicleService.createVehicle(request, customerId));
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/vehicle",
            consumes = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Set vehicle ",
            notes = "Edit existing vehicle based on id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - Vehicle successfully set:"),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ApiErrorResponse.class)})
    public ResponseEntity<Void> setVehicle(@RequestBody @Valid final SetVehicle request) {
        log.info("PUT api/vehicle with body: {}", request);
        vehicleService.setVehicle(request);
        return ResponseEntity.ok().build();
    }

}
