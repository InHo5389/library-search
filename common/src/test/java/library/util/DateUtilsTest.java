package library.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

    @Test
    @DisplayName("문자열(yyyyMMdd)을 LocalDate 객체로 변환한다")
    void parseYYYYMMDDShouldConvertStringToLocalDate() {
        // given
        String date = "20240101";

        // when
        LocalDate result = DateUtils.parse(date);

        // then
        assertThat(result).isEqualTo(LocalDate.of(2024, 1, 1));
    }
}