package com.malloneng.backend.infra.repository.memory.exception;

import com.malloneng.backend.domain.exception.ApplicationException;

public class AlreadyExistsException extends ApplicationException {
    public AlreadyExistsException() {
        super("Recurso já existe.");
    }
}
