package library.controller;

import jakarta.validation.Valid;
import library.controller.request.SearchRequest;
import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.service.BookApplicationService;
import library.service.BookQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookApplicationService bookApplicationService;

    @GetMapping
    public PageResult<SearchResponse> search(@Valid SearchRequest request) {
        return bookApplicationService.search(request.getQuery(), request.getPage(), request.getSize());
    }
}
