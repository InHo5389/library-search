package library.repository;

import library.NaverBookResponse;
import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.feign.NaverClient;
import library.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class NaverBookRepository implements BookRepository {

    private final NaverClient naverClient;

    @Override
    public PageResult<SearchResponse> search(String query, int page, int size) {
        NaverBookResponse response = naverClient.search(query, page, size);
        List<SearchResponse> responses = response.getItems().stream()
                .map(this::createResponse)
                .collect(Collectors.toList());
        return new PageResult<>(page, size, response.getTotal(), responses);
    }

    private SearchResponse createResponse(NaverBookResponse.NaverBookItemDto dto) {
        return SearchResponse.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .publisher(dto.getPublisher())
                .pubDate(DateUtils.parse(dto.getPubDate()))
                .isbn(dto.getIsbn())
                .build();
    }
}