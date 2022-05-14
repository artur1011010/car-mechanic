package pl.artur.zaczek.car.mechanic.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.artur.zaczek.car.mechanic.rest.error.BadRequestException;
import pl.artur.zaczek.car.mechanic.rest.model.CreateCustomer;
import pl.artur.zaczek.car.mechanic.rest.model.SetCustomer;

@Component
@Slf4j
public class ModelValidator {

    public void validateCreateCustomerRequest(final CreateCustomer customerRequest) {
        if (customerRequest == null) {
            log.error("Customer request can not be null");
            throw new BadRequestException("Customer request can not be null", HttpStatus.BAD_REQUEST.toString());
        }
        if (customerRequest.isCompany() && (StringUtils.isBlank(customerRequest.getCompanyNip()) || StringUtils.isBlank(customerRequest.getCompanyName()))) {
            log.error("Customer type company requires companyNip and companyName: {}", customerRequest);
            throw new BadRequestException("Customer type company requires companyNip and companyName", HttpStatus.BAD_REQUEST.toString());
        }
    }

    public void validateSetCustomerRequest(final SetCustomer customerRequest) {
        if (customerRequest == null) {
            log.error("Customer request can not be null");
            throw new BadRequestException("Customer request can not be null", HttpStatus.BAD_REQUEST.toString());
        }
        if (customerRequest.isCompany() && (StringUtils.isBlank(customerRequest.getCompanyNip()) || StringUtils.isBlank(customerRequest.getCompanyName()))) {
            log.error("Customer type company requires companyNip and companyName: {}", customerRequest);
            throw new BadRequestException("Customer type company requires companyNip and companyName", HttpStatus.BAD_REQUEST.toString());
        }
    }
}

