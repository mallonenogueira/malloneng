package com.malloneng.backend.application.repository;

import com.malloneng.backend.domain.entity.Search;
import com.malloneng.backend.domain.value.Id;

import java.util.Optional;

public interface SearchRepository {
    void create(Search search);
    void update(Search search);
    Optional<Search> findById(Id id);
}
