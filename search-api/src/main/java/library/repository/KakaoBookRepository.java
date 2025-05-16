package library.repository;

import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KakaoBookRepository implements BookRepository{

    @Override
    public PageResult<SearchResponse> search(String query, int page, int size) {
        return null;
    }
}
