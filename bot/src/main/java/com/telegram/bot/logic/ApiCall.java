package com.telegram.bot.logic;

import com.telegram.bot.domain.RomEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class ApiCall {

    private final String searchUrl;
    private final String filesUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final HtmlParser htmlParser = new HtmlParser();
    private final TelegramFormatter telegramFormatter = new TelegramFormatter();

    public ApiCall(@Value("${urls.search}") String searchUrl,
                   @Value("${urls.files}") String filesUrl) {
        this.searchUrl = searchUrl;
        this.filesUrl = filesUrl;
    }

    public String fetchHtml(String query) throws IOException, InterruptedException {
        String apiUrl = searchUrl + URLEncoder.encode(query, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        List<RomEntry> roms = htmlParser.parseRomTable(response.body());
        return telegramFormatter.formatRomList(roms, query, filesUrl);
    }
}
