package library.service;

import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.controller.response.StatResponse;
import library.entity.DailyStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookApplicationService {

    private final BookQueryService bookQueryService;
    private final DailyStatQueryService dailyStatQueryService;
    private final DailyStatCommandService dailyStatCommandService;

    /**
     * TODO
     * 외부 API를 호출을 하고 나서 이 통계값이 저장이 될 때까지 사용자는 응답을 받지 못함
     * 검색 결과를 빨리 받아보고 싶은데 그 내부적으로 DB 처리까지 기다려야 되는 상황
     */
    @Transactional
    public PageResult<SearchResponse> search(String query, int page, int size) {
        PageResult<SearchResponse> response = bookQueryService.search(query, page, size);
        dailyStatCommandService.save(new DailyStat(query, LocalDateTime.now()));

        return response;
    }

    @Transactional(readOnly = true)
    public StatResponse findQueryCount(String query, LocalDate date) {
        return dailyStatQueryService.findQueryCount(query, date);
    }
}
