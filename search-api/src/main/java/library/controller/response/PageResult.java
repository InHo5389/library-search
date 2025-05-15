package library.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResult<T> {

    @Schema(description = "현재 페이지번호", example = "1")
    private int page;
    @Schema(description = "페이지 크기", example = "10")
    private int size;
    @Schema(description = "전체 요소수", example = "100")
    private int totalElements;
    @Schema(description = "본문")
    private List<T> contents;
}
