package pl.arturzaczek.carMechanicDB.rest.error;

import lombok.Getter;

@Getter
public abstract class BaseApiError extends RuntimeException{
    protected String code;
    protected String details;

    public BaseApiError(String message, String code, String details) {
        super(message);
        this.code = code;
        this.details = details;
    }

    public BaseApiError(String code, String details) {
        this.code = code;
        this.details = details;
    }
}
