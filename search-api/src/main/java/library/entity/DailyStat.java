package library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 검색이 일어날 때마다 어떤 쿼리로 검색이 됐는지에 대한 정보들을 저장 하기 위한 클래스
 * 매일매일 수집이 돼야 되기 때문에 일간 통계 정보라고 표현
 */
@Getter
@Entity
@Table(name = "daily_stat")
@NoArgsConstructor
public class DailyStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String query;
    private LocalDateTime createdAt;

    public DailyStat(String query, LocalDateTime createdAt) {
        this.query = query;
        this.createdAt = createdAt;
    }
}
