package library.service;

import library.entity.DailyStat;
import library.repository.DailyStatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyStatCommandService {

    private final DailyStatRepository dailyStatRepository;

    @Transactional
    public DailyStat save(DailyStat dailyStat){
        log.info("save daily status: {}", dailyStat);
        return dailyStatRepository.save(dailyStat);
    }
}
