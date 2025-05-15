package library.config;

import library.ApiException;
import library.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        log.error("Api Exception, message={},className={}", e.getErrorMessage(), e.getClass().getName());
        return ResponseEntity.status(e.getHttpStatus())
                .body(new ErrorResponse(e.getErrorMessage(), e.getErrorType()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        log.error("NoResourceFound Exception occurred. message={}, className={}", e.getMessage(), e.getClass().getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorType.NO_RESOURCE.getDescription(),  ErrorType.NO_RESOURCE));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameter Exception occurred. parameterName={}, message={}", e.getParameterName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorType.MISSING_REQUEST_PARAMETER.getDescription(),  ErrorType.MISSING_REQUEST_PARAMETER));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatch Exception occurred. message={}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorType.METHOD_ARGUMENT_TYPE_MISMATCH.getDescription(),  ErrorType.METHOD_ARGUMENT_TYPE_MISMATCH));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(createMessage(e), ErrorType.INVALID_PAREMETER));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception, message={},className={}", e.getMessage(), e.getClass().getName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ErrorType.UNKNOWN.getDescription(), ErrorType.UNKNOWN));
    }

    private String createMessage(BindException e) {
        // getDefaultMessage()는 @Nullable이기에 메시지를 세팅하지 않으면 null값이 나올수 있음
        if (e.getFieldError() != null && e.getFieldError().getDefaultMessage() != null) {
            return e.getFieldError().getDefaultMessage();
        }

        return e.getFieldErrors().stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", ")) + " 값들이 정확하지 않습니다.";
    }
}
