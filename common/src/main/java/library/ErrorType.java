package library;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    EXTERNAL_API_ERROR("외부 API 호출 에러입니다."),
    UNKNOWN("알 수 없는 에러입니다."),
    INVALID_PAREMETER("잘못된 요청값입니다."),
    NO_RESOURCE("존재하지 않는 리소스입니다."),
    MISSING_REQUEST_PARAMETER("파라미터를 입력하지 않았습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH("메서드 인자 형식이 틀렸습니다.");

    private final String description;
}
