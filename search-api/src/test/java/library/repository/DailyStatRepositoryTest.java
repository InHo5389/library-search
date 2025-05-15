package library.repository;

import library.entity.DailyStat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class DailyStatRepositoryTest {

    @Autowired
    private DailyStatRepository dailyStatRepository;

    @Test
    @DisplayName("쿼리의 카운트를 조회한다.")
    void countByQueryAndCreatedAtBetween() {
        //given
        String query = "HTTP";
        LocalDateTime createdAt = LocalDateTime.of(2025, 5, 2, 0, 0, 0);
        DailyStat plusDailyStat1 = new DailyStat(query, createdAt.plusMinutes(10));
        DailyStat minusDailyStat = new DailyStat(query, createdAt.minusMinutes(10));
        DailyStat plusDailyStat2 = new DailyStat(query, createdAt.plusMinutes(10));
        DailyStat dailyStat = new DailyStat("java", createdAt.plusMinutes(10));

        Iterable<DailyStat> dailyStats = List.of(plusDailyStat1,minusDailyStat,plusDailyStat2,dailyStat);
        dailyStatRepository.saveAll(dailyStats);
        //when
        long count = dailyStatRepository.countByQueryAndCreatedAtBetween(query, createdAt, createdAt.plusDays(1));
        //then
        assertThat(count).isEqualTo(2);
    }
}