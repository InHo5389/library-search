package library;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class Document {
    String title;
    List<String> authors;
    String isbn;
    String publisher;
    String datetime;
}
