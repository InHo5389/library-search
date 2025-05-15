package library.repository;

import library.controller.response.StatResponse;
import library.entity.DailyStat;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Test
    @DisplayName("가장 많이 검색된 쿼리 키워드를 개수와 함께 상위 3개를 반환한다.")
    void findTopQuery(){
        //given
        LocalDateTime now = LocalDateTime.now();
        DailyStat stat1 = new DailyStat("HTTP", now.plusMinutes(10));
        DailyStat stat2 = new DailyStat("HTTP", now.plusMinutes(10));
        DailyStat stat3 = new DailyStat("HTTP", now.plusMinutes(10));
        DailyStat stat4 = new DailyStat("JAVA", now.plusMinutes(10));
        DailyStat stat5 = new DailyStat("JAVA", now.plusMinutes(10));
        DailyStat stat6 = new DailyStat("JAVA", now.plusMinutes(10));
        DailyStat stat7 = new DailyStat("JAVA", now.plusMinutes(10));
        DailyStat stat8 = new DailyStat("SPRING", now.plusMinutes(10));
        DailyStat stat9 = new DailyStat("SPRING", now.plusMinutes(10));
        DailyStat stat10 = new DailyStat("OS", now.plusMinutes(10));

        dailyStatRepository.saveAll(List.of(stat1, stat2, stat3, stat4, stat5, stat6, stat7, stat8, stat9, stat10));
        //when
        Pageable request = PageRequest.of(0, 3);
        List<StatResponse> responses = dailyStatRepository.findTopQuery(request);
        //then
        assertThat(responses).hasSize(3)
                .extracting("query","count")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("JAVA",4L),
                        Tuple.tuple("HTTP",3L),
                        Tuple.tuple("SPRING",2L)
                );
    }
}