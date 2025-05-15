package library.service;

import library.controller.response.StatResponse;
import library.repository.DailyStatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyStatQueryServiceTest {

    @InjectMocks
    private DailyStatQueryService dailyStatQueryService;

    @Mock
    private DailyStatRepository dailyStatRepository;

    @Test
    @DisplayName("findQueryCount 조회시 하루치를 조회하면서 쿼리개수가 반환된다.")
    void findQueryCount() {
        //given
        String query = "HTTP";
        LocalDate today = LocalDate.of(2025, 5, 16);
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        long count = 3L;
        when(dailyStatRepository.countByQueryAndCreatedAtBetween(query, startOfDay, endOfDay))
                .thenReturn(count);
        //when
        StatResponse response = dailyStatQueryService.findQueryCount(query, LocalDate.now());
        //then
        assertThat(response.getCount()).isEqualTo(count);
        assertThat(response.getQuery()).isEqualTo(query);
    }

    @Test
    @DisplayName("findTop5Query 조회시 상위 5개 반환 요청이 들어간다")
    void findTop5Query_ShouldRequestTop5Queries() {
        // given
        // when
        dailyStatQueryService.findTop5Query();

        // then
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(dailyStatRepository).findTopQuery(pageableCaptor.capture());

        Pageable capturedPageable = pageableCaptor.getValue();
        assertThat(capturedPageable.getPageNumber()).isEqualTo(0);
        assertThat(capturedPageable.getPageSize()).isEqualTo(5);
    }
}