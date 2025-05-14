package library;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class NaverBookResponse {

    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverBookItemDto> items;

    @Getter
    @ToString
    public static class NaverBookItemDto{
        private String title;
        private String link;
        private String image;
        private String author;
        private String discount;
        private String publisher;
        @JsonProperty("pubdate")
        private String pubDate;
        private String isbn;
        private String description;

        public NaverBookItemDto(String title, String author, String publisher, String pubDate, String isbn) {
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.pubDate = pubDate;
            this.isbn = isbn;
        }
    }
}
