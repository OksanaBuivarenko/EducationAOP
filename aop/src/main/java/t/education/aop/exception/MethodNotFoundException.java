package t.education.aop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MethodNotFoundException extends RuntimeException {
    public MethodNotFoundException(String method) {
        super("Could not find method with name " + method + ".");
    }
}

