package pl.artur.zaczek.car.mechanic.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.artur.zaczek.car.mechanic.model.ServiceRequest;
import pl.artur.zaczek.car.mechanic.rest.model.ServiceRequestResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ServiceRequestMapperTest {

    @Autowired
    ServiceRequestMapper serviceRequestMapper;

    @Test
    @DisplayName("ServiceRequestMapper - should return correct ServiceRequest")
    public void shouldReturnCorrectServiceRequest() {
        //given
        final ServiceRequest input = ServiceRequest.builder()
                .title("test")
                .price(new BigDecimal("1220"))
                .comment("test - comment")
                .id(1L)
                .finishTime(LocalDateTime.of(2022, 4, 9, 12, 20))
                .build();

        final ServiceRequestResponse expectedResponse = ServiceRequestResponse.builder()
                .title("test")
                .price(new BigDecimal("1220"))
                .comment("test - comment")
                .id(1L)
                .finishTime(LocalDateTime.of(2022, 4, 9, 12, 20))
                .build();
        //when
        final ServiceRequestResponse actualResponse = serviceRequestMapper.toServiceRequestResponse(input);
        //then
        assertEquals(expectedResponse, actualResponse);
    }

}