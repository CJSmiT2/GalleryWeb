package ua.org.smit.galleryweb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestFoundException extends RuntimeException {

    public BadRequestFoundException(String string) {
        super(string);
    }

}
