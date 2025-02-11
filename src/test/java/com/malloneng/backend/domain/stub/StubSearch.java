package com.malloneng.backend.domain.stub;

import com.malloneng.backend.domain.entity.Search;

public class StubSearch {
    public static Search aSearch() {
        return Search.create("test");
    }
}
