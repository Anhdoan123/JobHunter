package vn.anhdoan.jobhunter.service.error;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.anhdoan.jobhunter.domain.RestResponse;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = {
            // Lỗi khi kh tìm thấy người dùng
            UsernameNotFoundException.class,
            // Lỗi khi sai mật khẩu
            BadCredentialsException.class
    })
    public ResponseEntity<RestResponse> handleLoginException(Exception ex) {
        RestResponse res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Thông tin đăng nhập không hợp lệ");
        res.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(res);
    }

    // Khi validation bắt lỗi sẽ trả ra lỗi MethodArgumentNotValidException
    // Bắt lỗi MethodArgumentNotValidException để custom lỗi
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
