package library.controller;

import library.service.BookQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookQueryService bookQueryService;

    @Test
    @DisplayName("")
    void test() throws Exception {
        //given
        String givenQuery = "HTTP";
        int givenPage = 1;
        int givenSize = 10;
        //when
        //then
        mockMvc.perform(get("/v1/books")
                .param("query",givenQuery)
                .param("page",String.valueOf(givenPage))
                .param("size",String.valueOf(givenSize)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}