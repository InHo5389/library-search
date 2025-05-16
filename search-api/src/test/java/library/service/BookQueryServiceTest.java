package library.service;

import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookQueryServiceTest {
    
    @InjectMocks
    private BookQueryService bookQueryService;
    
    @Mock
    private BookRepository naverBookRepository;
    
    @Mock
    private BookRepository kakaoBookRepository;

    @BeforeEach
    void setUp() {
        bookQueryService = new BookQueryService(naverBookRepository, kakaoBookRepository);
    }
    
    @Test
    @DisplayName("search 인자가 그대로 넘어가고 naver쪽으로 호출되고 kakao쪽으로는 호출되지 않는다.")
    void test(){
        //given
        String query = "Http";
        int page = 1;
        int size = 10;
        int totalElements = 10;

        List<SearchResponse> searchResponses = List.of(
                new SearchResponse("HTTP1", "author1", "1", LocalDate.of(2025, 5, 15), "isbn1"),
                new SearchResponse("HTTP2", "author2", "2", LocalDate.of(2025, 5, 16), "isbn2")
        );
        PageResult<SearchResponse> result = new PageResult<>(page, size, totalElements, searchResponses);
        when(naverBookRepository.search(query,page,size)).thenReturn(result);
        //when
        PageResult<SearchResponse> response = bookQueryService.search(query, page, size);
        //then
        assertThat(response.getPage()).isEqualTo(page);
        assertThat(response.getSize()).isEqualTo(size);
        assertThat(response.getTotalElements()).isEqualTo(totalElements);

        verify(naverBookRepository).search(query, page, size);
        verifyNoInteractions(kakaoBookRepository);
    }
}