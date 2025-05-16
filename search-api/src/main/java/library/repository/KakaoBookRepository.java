package library.repository;

import library.Document;
import library.KakaoBookResponse;
import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.feign.KakaoClient;
import library.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class KakaoBookRepository implements BookRepository {

    private final KakaoClient kakaoClient;

    @Override
    public PageResult<SearchResponse> search(String query, int page, int size) {
        KakaoBookResponse response = kakaoClient.search(query, page, size);

        List<SearchResponse> responses = response.getDocuments().stream()
                .map(this::createResponse)
                .collect(Collectors.toList());
        return new PageResult<>(page,size,response.getMeta().getTotalCount(), responses);
    }

    private SearchResponse createResponse(Document document) {
        return SearchResponse.builder()
                .title(document.getTitle())
                .author(document.getAuthors().toString())
                .publisher(document.getPublisher())
                .pubDate(DateUtils.parseOffsetDateTime(document.getDatetime()).toLocalDate())
                .isbn(document.getIsbn())
                .build();
    }
}
