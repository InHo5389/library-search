package library.service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.repository.KakaoBookRepository;
import library.repository.NaverBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookQueryServiceIntegrationTest {

    @Autowired
    private BookQueryService bookQueryService;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @MockitoBean
    private KakaoBookRepository kakaoBookRepository;

    @MockitoBean
    private NaverBookRepository naverBookRepository;

    @Test
    @DisplayName("정상 상황에서는 Circuit의 상태가 CLOSED이고 naver쪽으로 호출이 들어간다.")
    void shouldBeClosedAndCallNaverRepository() {
        //given
        String query = "HTTP";
        int page = 1;
        int size = 10;

        when(naverBookRepository.search(query, page, size))
                .thenReturn(new PageResult<>(1, 10, 0, Collections.emptyList()));
        //when
        bookQueryService.search(query, page, size);
        //then
        verify(naverBookRepository, times(1)).search(query, page, size);

        CircuitBreaker circuitBreaker = circuitBreakerRegistry.getAllCircuitBreakers()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("CircuitBreaker not found"));

        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.CLOSED);
        verifyNoInteractions(kakaoBookRepository);
    }

    @Test
    @DisplayName("circuit이 open되면 kakao 쪽으로 요청을 한다.")
    void tes1t() {
        //given
        String query = "HTTP";
        int page = 1;
        int size = 10;
        PageResult<SearchResponse> kakaoResponse = new PageResult<>(1, 10, 0, Collections.emptyList());


        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .slidingWindowSize(1)
                .minimumNumberOfCalls(1)
                .failureRateThreshold(50)
                .build();
        circuitBreakerRegistry.circuitBreaker("naverSearch", config);

        when(naverBookRepository.search(query, page, size)).thenThrow(new RuntimeException("error!"));

        when(kakaoBookRepository.search(query, page, size)).thenReturn(kakaoResponse);

        //when
        PageResult<SearchResponse> result = bookQueryService.search(query, page, size);

        //then
        verify(kakaoBookRepository, times(1)).search(query, page, size);

        CircuitBreaker circuitBreaker = circuitBreakerRegistry.getAllCircuitBreakers()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("CircuitBreaker not found"));
        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);

        assertThat(result).isEqualTo(kakaoResponse);
    }
}