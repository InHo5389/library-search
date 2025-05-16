package library;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class KakaoBookResponse {

    List<Document> documents;
    Meta meta;
}
