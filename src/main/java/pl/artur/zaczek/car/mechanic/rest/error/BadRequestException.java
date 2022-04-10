package pl.artur.zaczek.car.mechanic.rest.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseApiError {
    public BadRequestException(String message, String code, String details) {
        super(message, code, details);
    }

    public BadRequestException(String code, String details) {
        super(code, details);
    }
}
