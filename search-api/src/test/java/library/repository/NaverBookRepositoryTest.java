package library.repository;

import library.NaverBookResponse;
import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.feign.NaverClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NaverBookRepositoryTest {

    private BookRepository bookRepository;

    @Mock
    private NaverClient naverClient;

    @BeforeEach
    void setup() {
        bookRepository = new NaverBookRepository(naverClient);
    }

    @Test
    @DisplayName("search호출 시 적절한 데이터 형식으로 변환한다.")
    void searchShouldConvertDataToProperFormat() {
        //given
        List<NaverBookResponse.NaverBookItemDto> items = List.of(
                new NaverBookResponse.NaverBookItemDto("제목1", "저자1", "출판사1", "20240101", "isbn1"),
                new NaverBookResponse.NaverBookItemDto("제목2", "저자2", "출판사2", "20240101", "isbn2")
        );

        NaverBookResponse response = new NaverBookResponse(
                "Wed, 29 May 2024 21:12:29 +0900",
                2,
                1,
                2,
                items
        );
        when(naverClient.search("HTTP", 1, 2)).thenReturn(response);
        //when
        PageResult<SearchResponse> result = bookRepository.search("HTTP", 1, 2);
        //then
        assertThat(result.getSize()).isEqualTo(2);
        assertThat(result.getPage()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContents()).hasSize(2);
        assertThat(result.getContents().get(0).getPubDate()).isEqualTo(LocalDate.of(2024, 1, 1));
    }
}