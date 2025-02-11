package com.malloneng.backend.application.usecase;

import com.malloneng.backend.application.repository.SearchRepository;
import com.malloneng.backend.domain.exception.SearchNotFoundException;
import com.malloneng.backend.domain.value.Id;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Pattern;

public class CrawlingUrlUseCase {
    private final String startUrl;
    private final SearchRepository searchRepository;


    public CrawlingUrlUseCase(String startUrl, SearchRepository searchRepository) {
        this.startUrl = startUrl;
        this.searchRepository = searchRepository;
    }

    public void execute(Id searchId) {
        try {
            var search = this.searchRepository
                    .findById(searchId)
                    .orElseThrow(SearchNotFoundException::new);

            var content = this.fetchPageContent(this.startUrl);

            if (this.hasKeyword(search.getKeyword(), content)) {
                //TODO: Criar addUrl no repositório
                search.addUrl(this.startUrl);
                this.searchRepository.update(search);
            }

            System.out.println(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasKeyword(String keyword, String content) {
        return Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE)
                .matcher(content)
                .find();
    }

    private String fetchPageContent(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
