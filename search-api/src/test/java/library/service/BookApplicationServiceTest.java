package library.service;

import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.entity.DailyStat;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookApplicationServiceTest {

    @InjectMocks
    private BookApplicationService bookApplicationService;

    @Mock
    private BookQueryService bookQueryService;

    @Mock
    private DailyStatCommandService dailyStatCommandService;

    @Test
    @DisplayName("search() 호출 시 검색 결과를 반환하면서 통계데이터를 저장한다.")
    void shouldSaveStatsWhenSearching() {
        //given
        String query = "Http";
        int page = 1;
        int size = 10;
        int totalElements = 10;
        LocalDateTime now = LocalDateTime.now();

        List<SearchResponse> searchResponses = List.of(
                new SearchResponse("HTTP1", "author1", "1", LocalDate.of(2025, 5, 15), "isbn1"),
                new SearchResponse("HTTP2", "author2", "2", LocalDate.of(2025, 5, 16), "isbn2")
        );
        PageResult<SearchResponse> result = new PageResult<>(page,size, totalElements,searchResponses);
        when(bookQueryService.search(query, page, size)).thenReturn(result);

        when(dailyStatCommandService.save(any()))
                .thenReturn(new DailyStat(query, now));
        //when
        PageResult<SearchResponse> result1 = bookApplicationService.search(query, page, size);
        //then
        assertThat(result1.getPage()).isEqualTo(page);
        assertThat(result1.getSize()).isEqualTo(size);
        assertThat(result1.getTotalElements()).isEqualTo(totalElements);
        assertThat(result1.getContents()).hasSize(2)
                        .extracting("title","author","isbn")
                                .containsExactlyInAnyOrder(
                                        Tuple.tuple("HTTP1","author1","isbn1"),
                                        Tuple.tuple("HTTP2","author2","isbn2")
                                );
    }
}