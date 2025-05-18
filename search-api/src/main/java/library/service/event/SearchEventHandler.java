package library.service.event;

import library.entity.DailyStat;
import library.service.DailyStatCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchEventHandler {

    private final DailyStatCommandService dailyStatCommandService;

    @Async
    @EventListener
    public void handleEvent(SearchEvent event) {
        log.info("[SearchEventHandler] handleEvent: {}", event);
        dailyStatCommandService.save(new DailyStat(event.getQuery(), event.getTimestamp()));
    }
}
