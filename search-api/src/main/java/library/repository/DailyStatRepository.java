package library.repository;

import library.entity.DailyStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface DailyStatRepository extends JpaRepository<DailyStat, Long> {
    long countByQueryAndCreatedAtBetween(String query, LocalDateTime start, LocalDateTime end);
}
