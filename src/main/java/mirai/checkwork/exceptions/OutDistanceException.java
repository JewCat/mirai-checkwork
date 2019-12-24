package mirai.checkwork.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OutDistanceException extends ResponseStatusException {
    public OutDistanceException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Out distance");
    }
}
