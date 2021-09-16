package com.example.redissession.controller;


import com.example.redissession.domain.ErrorMessage;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestControllerAdvince {
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorMessage handleAllException(Exception ex, WebRequest request) {
//        // quá trình kiểm soat lỗi diễn ra ở đây
//        return new ErrorMessage(10000, ex.getLocalizedMessage());
//    }

    /**
     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage TodoException(Exception ex, WebRequest request) {
        return new ErrorMessage(10100, "Đối tượng không tồn tại");
    }

    /**
     * UsernameNotFoundException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage todoUserNameNotFoundException() {
        return new ErrorMessage(10200, "Tên tài khoản không tồn tại");
    }

    /**
     * BadCredentialsException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBadCredentialsException() {
        return new ErrorMessage(10300, "Tài khoản mật khẩu không chính xác");
    }

    /**
     * DisabledException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleDisabledException() {
        return new ErrorMessage(10500, "Tài khoản bị khóa");
    }

    /**
     * DisabledException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleExpiredJwtException(Exception ex) {
        return new ErrorMessage(11100, ex.getMessage());
    }
        @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return new ErrorMessage(10000, ex.getLocalizedMessage());
    }
}
