package vn.anhdoan.jobhunter.service.error;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.anhdoan.jobhunter.domain.RestResponse;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = IdInvalidException.class)
    public ResponseEntity<RestResponse> handleIdException(IdInvalidException idInvalidException) {
        RestResponse res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Id Invalid Exception");
        res.setMessage(idInvalidException.getMessage());
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse> validationError(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        RestResponse res = new RestResponse<>();
        List<FieldError> fieldErrors = methodArgumentNotValidException.getFieldErrors();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Username or Password Invalid Exception");
        List<String> errors = fieldErrors.stream().map(f -> f.getDefaultMessage()).toList();
        res.setMessage(errors);
        return ResponseEntity.badRequest().body(res);
    }
}
