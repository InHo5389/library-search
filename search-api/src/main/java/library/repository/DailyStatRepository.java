package library.repository;

import library.controller.response.StatResponse;
import library.entity.DailyStat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyStatRepository extends JpaRepository<DailyStat, Long> {
    long countByQueryAndCreatedAtBetween(String query, LocalDateTime start, LocalDateTime end);

    @Query("SELECT new library.controller.response.StatResponse(ds.query, count(ds.query)) " +
            "FROM DailyStat ds " +
            "GROUP BY ds.query ORDER BY count(ds.query) DESC")
    List<StatResponse> findTopQuery(Pageable pageable);
}
