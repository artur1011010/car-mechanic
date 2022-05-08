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
import pl.artur.zaczek.car.mechanic.rest.model.CreateCustomer;
import pl.artur.zaczek.car.mechanic.rest.model.CustomerResponse;
import pl.artur.zaczek.car.mechanic.service.CustomerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/customer",
            produces = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return list of all customers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CustomerResponse[].class),
            @ApiResponse(code = 404, message = "Not found", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ApiErrorResponse.class)})
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        log.info("GET api/customer");
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/customer/{id}",
            produces = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return customer with specific id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CustomerResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ApiErrorResponse.class)})
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable final Long id) {
        log.info("GET api/customer/{} ", id);
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/customer",
            produces = "application/json; charset=UTF-8",
            consumes = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create customer",
            notes = "Creates a new customer and return created id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - User successfully created with id:", response = Long.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ApiErrorResponse.class)})
    public ResponseEntity<Long> createCustomer(@RequestBody @Valid final CreateCustomer customerRequest) {
        log.info("POST api/customer with requestBody=\n{}", customerRequest);
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }
}
