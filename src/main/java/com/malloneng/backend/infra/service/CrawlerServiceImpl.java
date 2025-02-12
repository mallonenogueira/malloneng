package com.malloneng.backend.infra.service;

import com.malloneng.backend.application.service.CrawlerService;

import java.net.URI;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CrawlerServiceImpl implements CrawlerService {
    @Override
    public boolean hasKeyword(String keyword, String content) {
        return Pattern
                .compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE)
                .matcher(content)
                .find();
    }

    @Override
    public Set<String> findLinks(String content) {
        String urlRegex = "<a\\s+[^>]*href=[\"']([^\"'#]+)[\"']";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(content);
        Set<String> contentLinks = new HashSet<>();

        while (urlMatcher.find()) {
            contentLinks.add(urlMatcher.group(1).trim());
        }

        return contentLinks;
    }

    @Override
    public Set<URI> createUriFromLinks(Set<String> links, String sourceUrl) {
        var source = URI.create(sourceUrl);

        return links.stream()
                .map(link -> {
                    try {
                        return URI.create(link);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(uri -> {
                    if (uri.isAbsolute()) {
                        return uri;
                    } else {
                        return source.resolve(uri);
                    }
                })
                .filter(this::isValid)
                .collect(Collectors.toSet());
    }

    private boolean isValid(URI uri) {
        return !"mailto".equals(uri.getScheme()) && uri.getHost() != null;
    }
}
