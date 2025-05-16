package library.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.Response;
import library.ApiException;
import library.KakaoErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KakaoErrorDecoderTest {

    @InjectMocks
    private KakaoErrorDecoder kakaoErrorDecoder;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("에러디코더에서 에러발생시 ApiException 예외가 throw 된다.")
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
                .thenReturn(new KakaoErrorResponse("AccessDeniedError", "cannot find Authorization : KakaoAK header"));

        //when
        //then
        assertThatThrownBy(() -> {
            kakaoErrorDecoder.decode("methodKey", response);
        })
                .isInstanceOf(ApiException.class)
                .hasMessage("cannot find Authorization : KakaoAK header");
    }
}