package library;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoErrorResponse {

    private String errorType;
    private String message;
}
