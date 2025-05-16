package library;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Meta {

    @JsonProperty(value = "is_end")
    private String isEnd;
    @JsonProperty(value = "pageable_count")
    private int pageableCount;
    @JsonProperty(value = "total_count")
    private int totalCount;
}
