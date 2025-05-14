package library.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    private String title;
    private String author;
    private String publisher;
    private LocalDate pubDate;
    private String isbn;
}
