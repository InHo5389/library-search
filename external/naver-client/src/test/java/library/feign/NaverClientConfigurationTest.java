package library.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class NaverClientConfigurationTest {

    NaverClientConfiguration configuration;

    @BeforeEach
    void setup() {
        configuration = new NaverClientConfiguration();
    }

    @Test
    @DisplayName("requestInterceptor의 header에 key값이 제대로 들어오는지 확인한다.")
    void addCorrectHeadersToRequest () {
        //given
        RequestTemplate requestTemplate = new RequestTemplate();
        String clientId = "id";
        String clientSecret = "secret";
        //when
        RequestInterceptor requestInterceptor = configuration.requestInterceptor(clientId, clientSecret);
        requestInterceptor.apply(requestTemplate);
        //then
        Map<String, Collection<String>> headers = requestTemplate.headers();
        assertThat(headers.get("X-Naver-Client-Id")).containsExactly(clientId);
        assertThat(headers.get("X-Naver-Client-Secret")).containsExactly(clientSecret);
    }

    @Test
    @DisplayName("requestInterceptor를 타기전엔 header가 존재하지 않는다.")
    void emptyTemplateHasNoHeaders () {
        //given
        RequestTemplate requestTemplate = new RequestTemplate();
        //when
        //then
        Map<String, Collection<String>> headers = requestTemplate.headers();
        assertThat(headers.get("X-Naver-Client-Id")).isNull();
        assertThat(headers.get("X-Naver-Client-Secret")).isNull();
    }
}