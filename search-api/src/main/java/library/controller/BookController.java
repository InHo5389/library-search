package library.controller;

import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.service.BookQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookQueryService bookQueryService;

    @GetMapping
    public PageResult<SearchResponse> search(
            @RequestParam("query") String query,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return bookQueryService.search(query, page, size);
    }
}
