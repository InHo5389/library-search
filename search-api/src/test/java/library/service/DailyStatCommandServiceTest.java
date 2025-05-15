package library.service;

import library.entity.DailyStat;
import library.repository.DailyStatRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DailyStatCommandServiceTest {

    @Mock
    private DailyStatRepository dailyStatRepository;

    @InjectMocks
    private DailyStatCommandService dailyStatCommandService;

    @Test
    @DisplayName("저장시 넘어온 인자 그대로 호출된다.")
    void test(){
        //given
        LocalDateTime now = LocalDateTime.now();
        String requestQuery = "HTTP";
        DailyStat dailyStat = new DailyStat(requestQuery, now);

        when(dailyStatRepository.save(any())).thenReturn(dailyStat);
        //when
        DailyStat savedDailyStat = dailyStatCommandService.save(dailyStat);
        //then
        assertThat(savedDailyStat.getCreatedAt()).isEqualTo(now);
        assertThat(savedDailyStat.getQuery()).isEqualTo(requestQuery);
    }

}