package library;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
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
    }
}
