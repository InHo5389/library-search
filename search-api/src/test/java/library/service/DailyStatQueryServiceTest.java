package library.service;

import library.controller.response.StatResponse;
import library.repository.DailyStatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
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
}