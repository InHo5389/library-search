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

/**
 * MockMvc
 * 실제 서블릿 컨테이너를 구동하지 않고도 빠르고 효율적으로 테스트 가능
 *
 * 실제로 API를 개발을 하고 나서 수정을 하고 재배포를 하거나 혹은 기능 추가가 되어서
 * 재배포를 할 때 저희가 실제 서버를 운영을 하고 있는 상태에서 서버를 다 내리고 나서 배포를 하거나 사실 그렇게 할 수 있는 경우가 많지 않습니다
 * 사용자의 경험에 굉장히 좋지 않기에 그래서 그런 경우에는 보통 버저닝을 해서 이런 식으로 이 API에 대한 첫
 * 번째 버전이라는 것을 명시해주고 이 API가 나중에 수정이 필요해졌을 때 대대적으로 개편이 일어날 때 이것을 똑같이 만들고 V2라는 API로 한 번을 더 만듭니다.
 *
 * 그리고 나서 이제 배포를 하게 되면은 이제 V1과 V2가 공존하는 때가 잠깐 있을 거에요.
 * 그때 저희 API의 소비처가 V1을 바라보던 것들을 이제 V2로 바라보게끔 배포를 한 이후에 저희 서버에서 V1 API를 이제 페이드아웃하는 이런식으로 전환을 이제 주로 하게
 * 되거든요. 그래서 저희도 그걸 좀 사용을 하기 위해서 v1이라는 네이밍을 좀 붙여 주도록 하겠습니다.
 */
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