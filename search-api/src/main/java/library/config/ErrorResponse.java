package library.config;

import library.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String errorMessage;
    private ErrorType errorType;
}
