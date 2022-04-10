package pl.artur.zaczek.car.mechanic.rest.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseApiError {
    public NotFoundException(String message, String code, String details) {
        super(message, code, details);
    }

    public NotFoundException(String code, String details) {
        super(code, details);
    }
}
