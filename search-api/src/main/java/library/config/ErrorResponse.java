package library.config;

import io.swagger.v3.oas.annotations.media.Schema;
import library.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "에러응답")
public class ErrorResponse {

    @Schema(description = "에러 메세지", example = "잘못된 요청입니다")
    private String errorMessage;
    @Schema(description = "에러 타입", example = "INVALID_PARAMETER")
    private ErrorType errorType;
}
