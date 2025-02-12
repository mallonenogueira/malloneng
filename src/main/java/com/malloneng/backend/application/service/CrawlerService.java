package com.malloneng.backend.application.service;

import java.net.URI;
import java.util.Set;

public interface CrawlerService {
    boolean hasKeyword(String keyword, String content);
    Set<String> findLinks(String content);
    Set<URI> createUriFromLinks(Set<String> links, String sourceUrl);
}
