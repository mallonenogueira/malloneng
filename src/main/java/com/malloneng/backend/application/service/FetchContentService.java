package com.malloneng.backend.application.service;

import java.io.IOException;
import java.net.URI;

public interface FetchContentService {
    String fetch(URI uri) throws IOException, InterruptedException;
}
