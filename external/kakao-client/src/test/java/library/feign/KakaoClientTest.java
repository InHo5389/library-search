package library.feign;

import library.KakaoBookResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = KakaoClientTest.TestConfig.class)
@ActiveProfiles("test")
class KakaoClientTest {

    @EnableAutoConfiguration
    @EnableFeignClients(clients = KakaoClient.class)
    static class TestConfig{}

    @Autowired
    private KakaoClient kakaoClient;

    @Test
    @DisplayName("")
    void callNaverSearch(){
        //given
        //when
        KakaoBookResponse response = kakaoClient.search("HTTP", 1, 10);
        System.out.println(response);
        //then
        assertThat(response).isNotNull();
    }
}