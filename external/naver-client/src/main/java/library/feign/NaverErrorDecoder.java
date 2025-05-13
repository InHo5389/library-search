package library.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import library.ApiException;
import library.ErrorType;
import library.NaverErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 디코드 안에 로직을 적어놓게 되면 에러가 발생했을 때 디코드 메서드 바디 안으로 들어와서 에러 처리
 */
@Slf4j
@RequiredArgsConstructor
public class NaverErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            NaverErrorResponse errorResponse = objectMapper.readValue(body, NaverErrorResponse.class);
            throw new ApiException(errorResponse.getErrorMessage(), ErrorType.EXTERNAL_API_ERROR, HttpStatus.valueOf(response.status()));
        } catch (IOException e) {
            log.error("[에러 메시지 파싱 에러] code={}, request={}, methodKey={}, errorMessage={}",
                    response.status(), response.request(), methodKey, e.getMessage());
            throw new ApiException("네이버 메시지 파싱에러", ErrorType.EXTERNAL_API_ERROR, HttpStatus.valueOf(response.status()));
        }
    }
}
