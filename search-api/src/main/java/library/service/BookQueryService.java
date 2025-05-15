package library.service;

import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookQueryService {

    private final BookRepository bookRepository;

    public PageResult<SearchResponse> search(String query, int page, int size) {
        return bookRepository.search(query, page, size);
    }
}
