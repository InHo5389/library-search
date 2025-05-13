package library.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import library.NaverErrorResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 디코드 안에 로직을 적어놓게 되면 에러가 발생했을 때 디코드 메서드 바디 안으로 들어와서 에러 처리
 */
@RequiredArgsConstructor
public class NaverErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            NaverErrorResponse errorResponse = objectMapper.readValue(body, NaverErrorResponse.class);
            throw new RuntimeException(errorResponse.getErrorMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
