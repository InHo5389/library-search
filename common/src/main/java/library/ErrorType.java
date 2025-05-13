package library;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    EXTERNAL_API_ERROR("외부 API 호출 에러입니다."),
    UNKNOWN("알 수 없는 에러입니다."),
    INVALID_PAREMETER("잘못된 요청값입니다.");

    private final String description;
}
