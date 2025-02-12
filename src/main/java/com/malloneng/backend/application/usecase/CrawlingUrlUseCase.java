package com.malloneng.backend.application.usecase;

import com.malloneng.backend.application.repository.SearchRepository;
import com.malloneng.backend.domain.exception.SearchNotFoundException;
import com.malloneng.backend.domain.value.Id;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlingUrlUseCase {
    private final String startUrl;
    private final SearchRepository searchRepository;
    private final Set<String> visited;

    public CrawlingUrlUseCase(String startUrl, SearchRepository searchRepository) {
        this.startUrl = startUrl;
        this.searchRepository = searchRepository;
        this.visited = new HashSet<>();
    }

    public void execute(Id searchId) {
        var search = this.searchRepository
                .findById(searchId)
                .orElseThrow(SearchNotFoundException::new);

        Queue<String> links = new LinkedList<>();
        links.add(this.startUrl);

        // TODO: adicionar multiplas threads

        while (!links.isEmpty()) {
            var link = links.poll();

            if (this.visited.contains(link)) {
                continue;
            }

            System.out.println("Visitando:" + link);

            var result = doCrawl(search.getKeyword(), link);
            this.visited.add(link);

            if (result.hasKeyword()) {
                //TODO: Criar addUrl no repositório
                search.addUrl(link);
                this.searchRepository.update(search);
            }

            result.links()
                    .stream()
                    .filter(newLink -> this.isSameDomain(newLink, this.startUrl))
                    .filter(newLink -> !this.visited.contains(newLink))
                    .forEach(links::offer);
        }
        System.out.println("Acabou..");
        search.finish();
        this.searchRepository.update(search);
    }

    private Result doCrawl(String keyword, String url) {
        try {
            var content = this.fetchPageContent(url);

            return new Result(
                    this.hasKeyword(keyword, content),
                    this.extractLinks(content, url)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isSameDomain(String url1, String url2) {
        try {
            return URI
                    .create(url1)
                    .getHost()
                    .equalsIgnoreCase(URI.create(url2).getHost());
        } catch (Exception e) {
            System.out.println("Erro no link: " + url1);
            System.out.println("Erro no link: " + url2);
            return false;
        }

    }

    private boolean hasKeyword(String keyword, String content) {
        return Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE)
                .matcher(content)
                .find();
    }

    private Set<String> extractLinks(String content, String sourceUri) {
        String urlRegex = "<a\\s+[^>]*href=[\"']([^\"'#]+)[\"']";
        var contentLinks = new HashSet<String>();
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(content);

        while (urlMatcher.find()) {
            var link = urlMatcher.group(1).trim();

            try {
                var uri = URI.create(link);

                if (!uri.isAbsolute()) {
                    var linkBase = URI.create(sourceUri);
                    contentLinks.add(linkBase.resolve(uri).toString());

                } else if (!"mailto".equals(uri.getScheme())) {
                    contentLinks.add(link);
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("URI Inválida: " + link);
            }
        }

        return contentLinks;
    }

    private String fetchPageContent(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //TODO: throw Error

        return response.body();
    }

    private static record Result(boolean hasKeyword, Set<String> links) {
    }
}
