package library.service.event;

import library.entity.DailyStat;
import library.service.DailyStatCommandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchEventHandlerTest {

    @InjectMocks
    private SearchEventHandler eventHandler;

    @Mock
    private DailyStatCommandService dailyStatCommandService;

    @Test
    @DisplayName("이벤트 핸들러가 DailyStat을 올바르게 저장하는지 검증")
    void test() {
        //given
        String query = "http";
        LocalDateTime timestamp = LocalDateTime.now();

        when(dailyStatCommandService.save(any())).thenReturn(new DailyStat(query, timestamp));
        //when
        eventHandler.handleEvent(new SearchEvent(query, timestamp));
        //then
        verify(dailyStatCommandService,times(1)).save(any(DailyStat.class));
    }
}