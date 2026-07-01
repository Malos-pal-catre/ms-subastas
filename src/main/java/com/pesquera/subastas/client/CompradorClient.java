package com.pesquera.subastas.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
@Component
public class CompradorClient {
    private final WebClient webClient;
    public CompradorClient(WebClient.Builder builder, @Value("${app.ms-compradores.url}") String url) {
        this.webClient = builder.baseUrl(url).build();
    }
    public Map obtenerCompradorPorId(Long id) {
        return webClient.get()
                .uri("/api/compradores/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    throw new RuntimeException("Comprador no encontrado con id: " + id);
                })
                .bodyToMono(Map.class)
                .block();
    }
}