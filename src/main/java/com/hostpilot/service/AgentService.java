package com.hostpilot.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AiService {

    private final WebClient client;

    @Value("${openai.api.key}")
    private String apiKey;

    public AiService(WebClient client) {
        this.client = client;
    }

    public String generateReply(String context, String userMessage) {

        String prompt = """
                Eres Hostpilot, un asistente para apartamentos turísticos.
                Usa SOLO la información del contexto del piso.
                Si el huésped pregunta algo que no está en el contexto, pide más detalles.

                CONTEXTO:
                %s

                MENSAJE DEL HUÉSPED:
                %s
                """.formatted(context, userMessage);

        // Aquí luego añadiremos la llamada real a OpenAI
        return "Respuesta generada (placeholder): " + userMessage;
    }
}
