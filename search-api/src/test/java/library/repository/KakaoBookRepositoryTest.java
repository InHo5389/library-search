package library.repository;

import library.Document;
import library.KakaoBookResponse;
import library.Meta;
import library.NaverBookResponse;
import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.feign.KakaoClient;
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
class KakaoBookRepositoryTest {

    private BookRepository bookRepository;

    @Mock
    private KakaoClient kakaoClient;

    @BeforeEach
    void setup() {
        bookRepository = new KakaoBookRepository(kakaoClient);
    }

    @Test
    @DisplayName("search호출 시 적절한 데이터 형식으로 변환한다.")
    void searchShouldConvertDataToProperFormat() {
        //given
        int totalCount = 1;
        int page = 1;
        int size = 2;

        List<Document> documents = List.of(
                new Document("HTTP", List.of("ria", "inno"), "isbn1", "publisher1", "2002-10-04T00:00:00.000+09:00"),
                new Document("JAVA", List.of("inno", "ria"), "isbn2", "publisher2", "2003-10-04T00:00:00.000+09:00")
        );

        Meta meta = new Meta("isEnd", 1, totalCount);
        KakaoBookResponse response = new KakaoBookResponse(
                documents, meta
        );

        when(kakaoClient.search("HTTP", page, size)).thenReturn(response);

        //when
        PageResult<SearchResponse> result = bookRepository.search("HTTP", 1, 2);

        //then
        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getPage()).isEqualTo(page);
        assertThat(result.getContents()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(totalCount);
        assertThat(result.getContents().get(0).getAuthor()).isEqualTo("ria");
        assertThat(result.getContents().get(1).getAuthor()).isEqualTo("inno");
    }
}