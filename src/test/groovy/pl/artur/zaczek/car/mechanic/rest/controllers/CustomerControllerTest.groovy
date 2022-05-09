package pl.artur.zaczek.car.mechanic.rest.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.artur.zaczek.car.mechanic.BaseControllerITSpec
import pl.artur.zaczek.car.mechanic.jpa.CustomerRepository
import spock.lang.Unroll


class CustomerControllerTest extends BaseControllerITSpec {
    private static final String CUSTOMER = "/api/customer"

    @Autowired
    CustomerRepository customerRepository

    @Autowired
    private MockMvc mockMvc

    def startup() {
        customerRepository.deleteAll();
    }

    def "should return empty result"() {
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
    }

    @Unroll
    def "should return #expectedStatus when #testCase"() {
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post(CUSTOMER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(body)
                .contentType(MediaType.APPLICATION_JSON))
        then:
        response.andExpect(MockMvcResultMatchers.status().is(expectedStatus))

        where:
        testCase       | body | expectedStatus
        'body is null' | "{}" | 400
    }
}
