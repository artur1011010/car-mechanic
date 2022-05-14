package pl.artur.zaczek.car.mechanic.rest.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.artur.zaczek.car.mechanic.BaseControllerITSpec
import pl.artur.zaczek.car.mechanic.jpa.CustomerRepository
import pl.artur.zaczek.car.mechanic.model.Customer
import spock.lang.Unroll

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CustomerControllerTest extends BaseControllerITSpec {
    private static final String CUSTOMER_URI = "/api/customer"

    @Autowired
    CustomerRepository customerRepository

    @Autowired
    private MockMvc mockMvc

    def startup() {
        customerRepository.deleteAll();
    }

    def "get-customer should return empty result"() {
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "get-customer should return correct result"() {
        given:
        def customer = Customer.builder().id(1L)
                .name("test")
                .lastName("test")
                .phoneNo("123")
                .build();
        customerRepository.save(customer);
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        then:
        response
                .andExpect(status().isOk())
    }

    @Unroll
    def "post-customer should return #expectedStatus when #testCase"() {
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(body)
                .contentType(MediaType.APPLICATION_JSON))
        then:
        response.andExpect(MockMvcResultMatchers.status().is(expectedStatus))

        where:
        testCase            | body                                                                                                  | expectedStatus
        'body is null'      | "{}"                                                                                                  | 400
        'body is empty #1'  | "{\"company\": false}"                                                                                | 400
        'body is empty #2'  | "{\"company\": true}"                                                                                 | 400
        'body is empty #3'  | "{\"company\": true,  \"companyName\": \"test\", \"companyNip\": \"123\", \"email\": \"edytowany \"}" | 400
        'phoneNo is empty'  | "{\"lastName\": \"Test\",\"name\": \"Test\", \"email\": \"test\"}"                                    | 400
        'email is empty'    | "{\"lastName\": \"Test\",\"name\": \"Test\", \"phoneNo\": \"123\"}"                                   | 400
        'lastName is empty' | "{\"name\": \"Test\", \"phoneNo\": \"123\", \"email\": \"test\"}"                                     | 400
        'name is empty'     | "{\"lastName\": \"Test\", \"phoneNo\": \"123\", \"email\": \"test\"}"                                 | 400
    }

    @Unroll
    def "set-customer should return #expectedStatus when #testCase"() {
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.put(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(body)
                .contentType(MediaType.APPLICATION_JSON))
        then:
        response.andExpect(MockMvcResultMatchers.status().is(expectedStatus))

        where:
        testCase            | body                                                                                                  | expectedStatus
        'body is null'      | "{}"                                                                                                  | 400
        'body is empty #1'  | "{\"company\": false}"                                                                                | 400
        'body is empty #2'  | "{\"company\": true}"                                                                                 | 400
        'body is empty #3'  | "{\"company\": true,  \"companyName\": \"test\", \"companyNip\": \"123\", \"email\": \"edytowany \"}" | 400
        'phoneNo is empty'  | "{\"lastName\": \"Test\",\"name\": \"Test\", \"email\": \"test\"}"                                    | 400
        'email is empty'    | "{\"lastName\": \"Test\",\"name\": \"Test\", \"phoneNo\": \"123\"}"                                   | 400
        'lastName is empty' | "{\"name\": \"Test\", \"phoneNo\": \"123\", \"email\": \"test\"}"                                     | 400
        'name is empty'     | "{\"lastName\": \"Test\", \"phoneNo\": \"123\", \"email\": \"test\"}"                                 | 400
    }
}
