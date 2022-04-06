package pl.arturzaczek.carMechanicDB.rest.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.arturzaczek.carMechanicDB.rest.error.ApiErrorResponse;
import pl.arturzaczek.carMechanicDB.rest.model.CreateCustomerRequest;
import pl.arturzaczek.carMechanicDB.rest.model.CustomerResponse;
import pl.arturzaczek.carMechanicDB.service.impl.CustomerServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/customer",
            produces = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiOperation(value = "return list of all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CustomerResponse[].class),
            @ApiResponse(code = 404, message = "Not found", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ApiErrorResponse.class)})
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/customer",
            produces = "application/json; charset=UTF-8",
            consumes = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiOperation(value = "Create user",
            notes = "This method creates a new user and return new created id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - User successfully created with id", response = Long.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ApiErrorResponse.class)})
    public ResponseEntity<Long> createCustomer(@RequestBody @Valid CreateCustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.createUser(customerRequest));
    }
}
