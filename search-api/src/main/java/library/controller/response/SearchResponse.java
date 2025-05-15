package library.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "검색결과")
public class SearchResponse {

    @Schema(description = "제목", example = "HTTP완벽가이드")
    private String title;
    @Schema(description = "저자", example = "데이빗고울리")
    private String author;
    @Schema(description = "출판사", example = "인사이트")
    private String publisher;
    @Schema(description = "출판일", example = "2015-01-01")
    private LocalDate pubDate;
    @Schema(description = "isbn", example = "9788966261208")
    private String isbn;
}
