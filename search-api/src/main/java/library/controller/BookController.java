package library.controller;

import jakarta.validation.Valid;
import library.controller.request.SearchRequest;
import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.controller.response.StatResponse;
import library.service.BookApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookApplicationService bookApplicationService;

    @GetMapping
    public PageResult<SearchResponse> search(@Valid SearchRequest request) {
        log.info("[BookController] search={}", request);
        return bookApplicationService.search(request.getQuery(), request.getPage(), request.getSize());
    }

    @GetMapping("/stats")
    public StatResponse findQueryStats(@RequestParam String query, @RequestParam LocalDate date) {
        log.info("[BookController] find stats query={}, date={}", query, date);
        return bookApplicationService.findQueryCount(query, date);
    }

    @GetMapping("/stats/ranking")
    public List<StatResponse> findTop5Query() {
        log.info("[BookController] find top 5 stats");
        return bookApplicationService.findTop5Query();
    }
}
