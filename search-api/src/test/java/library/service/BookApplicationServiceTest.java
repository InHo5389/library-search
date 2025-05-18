package library.service;

import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.controller.response.StatResponse;
import library.entity.DailyStat;
import library.service.event.SearchEvent;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookApplicationServiceTest {

    @InjectMocks
    private BookApplicationService bookApplicationService;

    @Mock
    private BookQueryService bookQueryService;

    @Mock
    private DailyStatQueryService dailyStatQueryService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    @DisplayName("search() 호출 시 검색 결과를 반환하면서 통계데이터를 이벤트를 발행한다.")
    void shouldSaveStatsWhenSearching() {
        //given
        String query = "Http";
        int page = 1;
        int size = 10;
        int totalElements = 10;

        List<SearchResponse> searchResponses = List.of(
                new SearchResponse("HTTP1", "author1", "1", LocalDate.of(2025, 5, 15), "isbn1"),
                new SearchResponse("HTTP2", "author2", "2", LocalDate.of(2025, 5, 16), "isbn2")
        );
        PageResult<SearchResponse> expectedResult = new PageResult<>(page, size, totalElements, searchResponses);
        when(bookQueryService.search(query, page, size)).thenReturn(expectedResult);

        //when
        PageResult<SearchResponse> result = bookApplicationService.search(query, page, size);
        //then
        verify(bookQueryService, times(1)).search(query, page, size);

        assertThat(result).isEqualTo(expectedResult);

        ArgumentCaptor<SearchEvent> eventCaptor = ArgumentCaptor.forClass(SearchEvent.class);
        verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());

        SearchEvent capturedEvent = eventCaptor.getValue();
        assertThat(capturedEvent).isNotNull();
        assertThat(capturedEvent.getQuery()).isEqualTo(query);
    }

    @Test
    @DisplayName("findQueryCount호출시 query와 count를 그대로 넘긴다.")
    void findQueryCount() {
        //given
        String query = "HTTP";
        LocalDate date = LocalDate.now();
        when(dailyStatQueryService.findQueryCount(query, date))
                .thenReturn(new StatResponse(query, 2));
        //when
        StatResponse response = bookApplicationService.findQueryCount(query, date);
        //then
        assertThat(response.getCount()).isEqualTo(2);
        assertThat(response.getQuery()).isEqualTo(query);
    }

    @Test
    @DisplayName("findTop5Query 호출 시 상위 5개가 응답된다.")
    void findTop5Query() {
        //given
        List<StatResponse> searchResponses = List.of(
                new StatResponse("HTTP", 10L),
                new StatResponse("JAVA", 8L),
                new StatResponse("KAFKA", 7L),
                new StatResponse("PYTHON", 3L),
                new StatResponse("DOCKER", 2L)
        );
        when(dailyStatQueryService.findTop5Query()).thenReturn(searchResponses);
        //when
        List<StatResponse> responseList = bookApplicationService.findTop5Query();
        //then
        assertThat(responseList).hasSize(5)
                .extracting("query", "count")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("HTTP", 10L),
                        Tuple.tuple("JAVA", 8L),
                        Tuple.tuple("KAFKA", 7L),
                        Tuple.tuple("PYTHON", 3L),
                        Tuple.tuple("DOCKER", 2L)
                );
    }
}