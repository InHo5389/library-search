package library.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResult<T> {

    private int page;
    private int size;
    private int totalElements;
    private List<T> contents;
}
