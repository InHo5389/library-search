package library.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.Response;
import library.NaverErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NaverErrorDecoderTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private NaverErrorDecoder errorDecoder;

    @Test
    @DisplayName("에러디코더에서 에러발생시 RuntimeException 예외가 throw 된다.")
    void throwsRuntimeExceptionWhenErrorOccurs() throws IOException {
        //given
        Response.Body responseBody = mock(Response.Body.class);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[0]);

        Request request = mock(Request.class);

        Response response = Response.builder()
                .status(400)
                .request(request)
                .body(responseBody)
                .build();

        when(responseBody.asInputStream()).thenReturn(inputStream);
        when(objectMapper.readValue(anyString(), any(Class.class)))
                .thenReturn(new NaverErrorResponse("error!!", "SE03"));

        //when
        //then
        assertThatThrownBy(() -> {
            errorDecoder.decode("methodKey", response);
        })
                .isInstanceOf(RuntimeException.class)
                .hasMessage("error!!");
    }
}