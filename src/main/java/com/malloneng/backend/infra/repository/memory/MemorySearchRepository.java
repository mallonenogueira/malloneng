package com.malloneng.backend.infra.repository.memory;

import com.malloneng.backend.application.repository.SearchRepository;
import com.malloneng.backend.domain.entity.Search;
import com.malloneng.backend.infra.repository.memory.exception.AlreadyExistsException;
import com.malloneng.backend.domain.value.Id;

import java.util.Map;
import java.util.Optional;

public class MemorySearchRepository implements SearchRepository {
    private final Map<Id, Search> table;

    public MemorySearchRepository(Map<Id, Search> table) {
        this.table = table;
    }


    @Override
    public void create(Search search) {
        if (table.containsKey(search.getId())) {
            throw new AlreadyExistsException();
        }

        table.put(search.getId(), search);
    }

    @Override
    public void update(Search search) {
        if (!table.containsKey(search.getId())) {
            throw new AlreadyExistsException();
        }

        table.replace(search.getId(), search);
    }

    @Override
    public Optional<Search> findById(Id id) {
        return Optional.ofNullable(table.get(id));
    }
}
