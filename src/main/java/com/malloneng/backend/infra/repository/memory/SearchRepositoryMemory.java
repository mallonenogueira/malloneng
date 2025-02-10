package com.malloneng.backend.infra.repository.memory;

import com.malloneng.backend.application.repository.SearchRepository;
import com.malloneng.backend.domain.entity.Search;
import com.malloneng.backend.domain.value.Id;

import java.util.Map;
import java.util.Optional;

public class SearchRepositoryMemory implements SearchRepository {
    private final Map<String, Search> table;

    public SearchRepositoryMemory(Map<String, Search> table) {
        this.table = table;
    }


    @Override
    public void create(Search search) {
        if (table.containsKey(search.getId().getValue())) {
            //TODO
            throw new RuntimeException("Já existe search");
        }

        table.put(search.getId().getValue(), search);
    }

    @Override
    public void update(Search search) {
        if (!table.containsKey(search.getId().getValue())) {
            //TODO
            throw new RuntimeException("Search not found");
        }

        table.replace(search.getId().getValue(), search);
    }

    @Override
    public Optional<Search> findById(Id id) {
        return Optional.ofNullable(table.get(id.getValue()));
    }
}
