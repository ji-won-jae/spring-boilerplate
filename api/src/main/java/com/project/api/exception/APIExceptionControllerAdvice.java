package com.project.api.exception;

import com.project.api.model.common.ErrorResponse;
import com.project.api.model.common.ResponseCode;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ControllerAdvice(annotations = {RestController.class, ResponseBody.class})
public class APIExceptionControllerAdvice {

    //== 400
    @ExceptionHandler({BadRequestException.class, HttpMessageNotReadableException.class, NullPointerException.class,
            ResponseStatusException.class, ValidationException.class})
    public ResponseEntity<?> badRequest(HttpServletRequest request, Throwable e) {
        return responseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // == 401
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<?> handleUnAuthorizedException(HttpServletRequest request, Throwable e) {
        return responseEntity(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    //== 403
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> forbidden(HttpServletRequest request, Throwable e) {
        return responseEntity(HttpStatus.FORBIDDEN, e.getMessage());
    }

    //== 404
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> notFound(HttpServletRequest request, Throwable e) {
        return responseEntity(HttpStatus.NOT_FOUND, e.getMessage());
    }

    //== 405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> methodNotSupport(HttpServletRequest request, Throwable e) {
        return responseEntity(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed");
    }

    // == 409
    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<?> handleAlreadyExistMobileException(HttpServletRequest request, Throwable e) {
        return responseEntity(HttpStatus.CONFLICT, e.getLocalizedMessage());
    }

    // == 415
    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ResponseEntity<?> handleUnsupportedMediaTypeException(HttpServletRequest request, Throwable e) {
        return responseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
    }

    //== 500
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtime(HttpServletRequest request, Throwable e) {
        return responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
    }

    private ResponseEntity<?> responseEntity(HttpStatus status, String message) {
        return new ResponseEntity<>(new ErrorResponse(status.value(), message), new HttpHeaders(), status);
    }

}
