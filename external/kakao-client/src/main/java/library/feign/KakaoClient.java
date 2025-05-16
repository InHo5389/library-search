package library.feign;

import library.KakaoBookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "KakaoClient", url = "${external.kakao.url}", configuration = KakaoClientConfiguration.class)
public interface KakaoClient {

    @GetMapping("/v3/search/book")
    KakaoBookResponse search(
            @RequestParam("query") String query,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );
}
