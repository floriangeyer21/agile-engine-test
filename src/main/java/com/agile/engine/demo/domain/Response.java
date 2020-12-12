package com.agile.engine.demo.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private List<Picture> pictures;
    private int page;
    private int pageCount;
    private boolean hasMore;
}
