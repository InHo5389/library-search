package library.service;

import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.controller.response.StatResponse;
import library.entity.DailyStat;
import library.service.event.SearchEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookApplicationService {

    private final BookQueryService bookQueryService;
    private final DailyStatQueryService dailyStatQueryService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public PageResult<SearchResponse> search(String query, int page, int size) {
        PageResult<SearchResponse> response = bookQueryService.search(query, page, size);
        if (!response.getContents().isEmpty()){
            log.info("검색 결과 개수: {}",response.getSize());
            eventPublisher.publishEvent(new SearchEvent(query, LocalDateTime.now()));
        }

        return response;
    }

    @Transactional(readOnly = true)
    public StatResponse findQueryCount(String query, LocalDate date) {
        return dailyStatQueryService.findQueryCount(query, date);
    }

    @Transactional(readOnly = true)
    public List<StatResponse> findTop5Query() {
        return dailyStatQueryService.findTop5Query();
    }
}
