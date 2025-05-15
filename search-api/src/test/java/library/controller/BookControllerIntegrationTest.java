package library.controller;

import library.controller.request.SearchRequest;
import library.controller.response.PageResult;
import library.controller.response.SearchResponse;
import library.service.BookQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookQueryService bookQueryService;

    @Test
    @DisplayName("정상인자로 요청시 성공한다.")
    void returnSuccessWithValidParams() throws Exception {
        //given
        String query = "HTTP";
        int page = 1;
        int size = 10;
        SearchRequest request = new SearchRequest(query, page, size);
        int totalElements = 10;
        PageResult<SearchResponse> result = new PageResult<>(page,size, totalElements, List.of());

        when(bookQueryService.search(query,page,size))
                .thenReturn(result);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query",request.getQuery())
                .param("page",String.valueOf(request.getPage()))
                .param("size",String.valueOf(request.getSize()))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(10))
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.contents").isArray());
    }

    @Test
    @DisplayName("query가 비어있을때 BadRequest 응답반환된다.")
    void returnBadRequestWhenQueryIsEmpty() throws Exception {
        //given
        String query = "";
        int page = 1;
        int size = 10;
        SearchRequest request = new SearchRequest(query, page, size);
        int totalElements = 10;
        PageResult<SearchResponse> result = new PageResult<>(page,size, totalElements, List.of());

        when(bookQueryService.search(query,page,size))
                .thenReturn(result);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                        .param("query",request.getQuery())
                        .param("page",String.valueOf(request.getPage()))
                        .param("size",String.valueOf(request.getSize()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(containsString("책 제목은 비어있을 수 없습니다.")))
                .andExpect(jsonPath("$.errorType").value("INVALID_PAREMETER"));
    }

    @Test
    @DisplayName("page가 음수일경우에 BadRequest 응답반환된다.")
    void returnBadRequestWhenPageIsNegative() throws Exception {
        //given
        String query = "HTTP";
        int page = -1;
        int size = 10;
        SearchRequest request = new SearchRequest(query, page, size);
        int totalElements = 10;
        PageResult<SearchResponse> result = new PageResult<>(page,size, totalElements, List.of());

        when(bookQueryService.search(query,page,size))
                .thenReturn(result);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                        .param("query",request.getQuery())
                        .param("page",String.valueOf(request.getPage()))
                        .param("size",String.valueOf(request.getSize()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(containsString("페이지 번호는 1이상이어야 합니다.")))
                .andExpect(jsonPath("$.errorType").value("INVALID_PAREMETER"));
    }
    @Test
    @DisplayName("size가 10001을 초과하면 BadRequest 응답반환된다.")
    void returnBadRequestWhenSizeExceedsLimit() throws Exception {
        //given
        String query = "HTTP";
        int page = 1;
        int size = 10001;
        SearchRequest request = new SearchRequest(query, page, size);
        int totalElements = 10;
        PageResult<SearchResponse> result = new PageResult<>(page,size, totalElements, List.of());

        when(bookQueryService.search(query,page,size))
                .thenReturn(result);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                        .param("query",request.getQuery())
                        .param("page",String.valueOf(request.getPage()))
                        .param("size",String.valueOf(request.getSize()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(containsString("페이지 크기는 10000이하여야 합니다.")))
                .andExpect(jsonPath("$.errorType").value("INVALID_PAREMETER"));
    }
}