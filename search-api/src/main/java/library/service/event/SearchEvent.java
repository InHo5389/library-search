package library.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SearchEvent {

    private String query;
    private LocalDateTime timestamp;
}
