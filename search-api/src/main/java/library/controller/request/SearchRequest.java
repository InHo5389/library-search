package library.controller.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class SearchRequest {

    @NotBlank(message = "책 제목은 비어있을 수 없습니다.")
    @Size(max = 50,message = "책 제목은 최대 50자를 초과할 수 없습니다.")
    private String query;

    @Positive
    @NotNull(message = "페이지 번호는 필수입니다.")
    @Min(value = 1,message = "페이지 번호는 1이상이어야 합니다.")
    @Max(value = 10000,message = "페이지 번호는 10000이하여야 합니다.")
    private Integer page;

    @NotNull(message = "페이지 사이즈는 필수입니다.")
    @Min(value = 1,message = "페이지 크기는 1이상이어야 합니다.")
    @Max(value = 10000,message = "페이지 크기는 10000이하여야 합니다.")
    private Integer size;
}
