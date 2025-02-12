package com.malloneng.backend.application.usecase;

import com.malloneng.backend.application.repository.SearchRepository;
import com.malloneng.backend.application.service.CrawlerService;
import com.malloneng.backend.application.service.FetchContentService;
import com.malloneng.backend.domain.entity.Search;
import com.malloneng.backend.domain.exception.SearchNotFoundException;
import com.malloneng.backend.domain.value.Id;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CrawlSearchUseCase {
    private final URI starterUrl;
    private final SearchRepository searchRepository;
    private final FetchContentService fetchContentService;
    private final Set<URI> visited = ConcurrentHashMap.newKeySet();
    private final ExecutorService executor;
    private final CrawlerService crawler;

    public CrawlSearchUseCase(
            int numThreds,
            String startUrl,
            SearchRepository searchRepository,
            FetchContentService fetchContentService,
            CrawlerService crawler) {
        this.executor = Executors.newFixedThreadPool(numThreds);
        this.starterUrl = URI.create(startUrl);
        this.searchRepository = searchRepository;
        this.fetchContentService = fetchContentService;
        this.crawler = crawler;
    }

    public void execute(Id searchId) {
        var search = this.searchRepository.findById(searchId).orElseThrow(SearchNotFoundException::new);
        // TODO: Log
        System.out.println("Iniciando: " + searchId.getValue() + " " + LocalDateTime.now());

        CompletableFuture<Void> future = crawl(search, starterUrl).thenRun(() -> {
            // TODO: Log
            System.out.println("Crawling finalizado." + searchId.getValue() + " " + LocalDateTime.now());

            search.finish();
            this.searchRepository.update(search);

            executor.shutdown();

            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        });

        future.join();
    }

    private CompletableFuture<Void> crawl(Search search, URI currentUrl) {
        if (!visited.add(currentUrl)) return CompletableFuture.completedFuture(null);

        return fetchContentFromSite(currentUrl)
                .thenApplyAsync(content -> this
                        .proccessContentSearch(search, currentUrl, content).stream()
                        .filter(link -> !visited.contains(link))
                        .collect(Collectors.toSet()
                        ), executor)
                .thenComposeAsync(nextUrls -> CompletableFuture.allOf(
                        nextUrls.stream()
                                .map(link -> crawl(search, link))
                                .toList()
                                .toArray(new CompletableFuture[0])
                ), executor);
    }

    public Set<URI> proccessContentSearch(Search search, URI currentUrl, String content) {
        boolean hasKeyword = crawler.hasKeyword(search.getKeyword(), content);

        if (hasKeyword) {
            search.addUrl(currentUrl.toString());
            this.searchRepository.update(search);
        }

        Set<URI> links = crawler.createUriFromLinks(crawler.findLinks(content), currentUrl.toString());

        return links.stream()
                .filter(link -> this.isSameDomain(link, starterUrl))
                .collect(Collectors.toSet());
    }

    private CompletableFuture<String> fetchContentFromSite(URI uri) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return fetchContentService.fetch(uri);
            } catch (IOException e) {
                // TODO: Log
                return "";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "";
            }
        }, executor);
    }

    public boolean isSameDomain(URI uri1, URI uri2) {
        return uri1.getHost() != null &&
               uri2.getHost() != null &&
               uri1.getHost().equalsIgnoreCase(uri2.getHost());
    }
}
