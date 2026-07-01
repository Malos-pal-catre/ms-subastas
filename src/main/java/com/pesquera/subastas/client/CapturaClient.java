package com.pesquera.subastas.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
@Component
public class CapturaClient {
    private final WebClient webClient;
    public CapturaClient(WebClient.Builder builder, @Value("${app.ms-capturas.url}") String url) {
        this.webClient = builder.baseUrl(url).build();
    }
    public Map obtenerCapturaPorId(Long id) {
        return webClient.get()
                .uri("/api/capturas/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    throw new RuntimeException("Captura no encontrada con id: " + id);
                })
                .bodyToMono(Map.class)
                .block();
    }
}