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
    private final List<String> links;
    private final Set<String> visited;

    public CrawlingUrlUseCase(String startUrl, SearchRepository searchRepository) {
        this.startUrl = startUrl;
        this.searchRepository = searchRepository;
        this.links = new ArrayList<>();
        this.visited = new HashSet<>();
        links.add(startUrl);
    }

    public void execute(Id searchId) {
        var search = this.searchRepository
                .findById(searchId)
                .orElseThrow(SearchNotFoundException::new);
        var listIterator = links.listIterator();

        while(listIterator.hasNext()) {
            var link = listIterator.next();
            var result = doCrawl(search.getKeyword(), link);

            if (result.hasKeyword()) {
                //TODO: Criar addUrl no repositório
                search.addUrl(link);
                this.searchRepository.update(search);
            }

            visited.add(link);

            List<String> newLinks = result.links()
                    .stream()
                    .filter(l -> !visited.contains(l))
                    .filter(newLink -> this.isSameDomain(newLink, this.startUrl))
                    .toList();

            newLinks.forEach(l -> {
                listIterator.add(l);
                listIterator.previous();
            });
        }

        search.finish();
        this.searchRepository.update(search);
    }

    private Result doCrawl(String keyword, String url) {
        try {
            var content = this.fetchPageContent(url);

            return new Result(
                    this.hasKeyword(keyword, content),
                    this.extractLinks(content)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isSameDomain(String url1, String url2) {
        return URI
                .create(url1)
                .getHost()
                .equalsIgnoreCase(URI.create(url2).getHost());
    }

    private boolean hasKeyword(String keyword, String content) {
        return Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE)
                .matcher(content)
                .find();
    }

    private List<String> extractLinks(String content) {
        String urlRegex = "<a\\s+[^>]*href=[\"']([^\"'#]+)[\"']";
        List<String> contentLinks = new ArrayList<>();
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(content);


        while (urlMatcher.find()) {
            String link = urlMatcher.group(1).trim();

            if (URI.create(link).isAbsolute()) {
                contentLinks.add(link);
            } else {
                var linkBase = URI.create("http://hiring.axreng.com");
                contentLinks.add(linkBase.resolve(URI.create(link)).toString());
            }
            //TODO: file links
            //        String domain = this.url.getProtocol() + "://" + this.url.getHost();
//            if (link.startsWith("/")) {
//                link = baseDomain + link;
//            }


            //OLD REGEX
            //"((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)"
//            contentLinks.add(content.substring(urlMatcher.start(0), urlMatcher.end(0)));

        }

        return contentLinks;
    }

    private String fetchPageContent(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static record Result(boolean hasKeyword, List<String> links) {
    }
}
