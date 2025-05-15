package library.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "검색통계")
public class StatResponse {

    @Schema(description = "쿼리", example = "HTTP")
    private String query;
    @Schema(description = "검색횟수", example = "10")
    private long count;
}
