package library;

import library.entity.DailyStat;
import library.repository.DailyStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {

    private final DailyStatRepository dailyStatRepository;

    @Override
    public void run(String... args) throws Exception {
        DailyStat stat1 = new DailyStat("HTTP", LocalDateTime.now());
        DailyStat stat2 = new DailyStat("HTTP", LocalDateTime.now());
        DailyStat stat3 = new DailyStat("HTTP", LocalDateTime.now());
        DailyStat stat4 = new DailyStat("HTTP", LocalDateTime.now());
        DailyStat stat5 = new DailyStat("HTTP", LocalDateTime.now());
        DailyStat stat6 = new DailyStat("HTTP", LocalDateTime.now());
        DailyStat stat7 = new DailyStat("HTTP", LocalDateTime.now());

        DailyStat stat8 = new DailyStat("JAVA", LocalDateTime.now());
        DailyStat stat9 = new DailyStat("JAVA", LocalDateTime.now());
        DailyStat stat10 = new DailyStat("JAVA", LocalDateTime.now());
        DailyStat stat11 = new DailyStat("JAVA", LocalDateTime.now());
        DailyStat stat12 = new DailyStat("JAVA", LocalDateTime.now());

        DailyStat stat13 = new DailyStat("KOTLIN", LocalDateTime.now());
        DailyStat stat14 = new DailyStat("KOTLIN", LocalDateTime.now());
        DailyStat stat15 = new DailyStat("KOTLIN", LocalDateTime.now());
        DailyStat stat16 = new DailyStat("KOTLIN", LocalDateTime.now());

        DailyStat stat17 = new DailyStat("Database", LocalDateTime.now());

        DailyStat stat18 = new DailyStat("OS", LocalDateTime.now());
        DailyStat stat19 = new DailyStat("OS", LocalDateTime.now().plusDays(2));

        dailyStatRepository.saveAll(List.of(
                stat1, stat2, stat3, stat4, stat5, stat6, stat7,
                stat8, stat9, stat10, stat11, stat12, stat13, stat14,
                stat15, stat16, stat17, stat18, stat19
        ));
    }
}
