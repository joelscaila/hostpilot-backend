package com.hostpilot.ai;

import com.hostpilot.dto.AiRequest;
import com.hostpilot.dto.AgentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class AiService {

    private final WebClient webClient;

    @Value("${ollama.url}")
    private String aiUrl;

    @Value("${ollama.model}")
    private String model;

    public AiService() {
        this.webClient = WebClient.builder().build();
    }

    public String generateReply(String context, String userMessage) {

        String prompt = """
                You are Hostpilot, an AI assistant for vacation rental hosts.

                LANGUAGE RULES:
                - Detect automatically the guest's language.
                - Always reply in the same language the guest uses.
                - If the guest mixes languages, reply in the dominant one.
                - Keep responses short, clear and friendly (2–3 sentences max).

                BEHAVIOR RULES:
                - Use ONLY the property context provided.
                - If the guest asks something that is NOT explicitly in the property context, reply:
                   "Lo siento, no tengo esa información. ¿Podrías confirmarlo con el anfitrión?" but in the guest's language.
                - Never invent services, rules, amenities, schedules, or details.
                - If the guest asks for recommendations or local info not in the context, politely say you don't have that information.
                - Always be friendly and helpful.
                - If the guest expresses frustration or a problem, respond with empathy and clarity.

                PROPERTY CONTEXT:
                %s

                GUEST MESSAGE:
                %s
                """.formatted(context, userMessage);

        log.info("PROMPT ENVIADO:\n{}", prompt);

        AiRequest request = new AiRequest(model, prompt);

        AgentResponse response = webClient.post()
                .uri(aiUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AgentResponse.class)
                .block();

        if (response == null || response.getResponse() == null) {
            return "Lo siento, ha ocurrido un error procesando la respuesta.";
        }

        log.info("RESPUESTA OBTENIDA:\n{}", response.getResponse());

        return response.getResponse();
    }

    public String strictTranslate(String text, String lang) {

        String prompt = """
            You are a professional translator.
            Translate the text to %s.

            HARD RULES:
            - Respond ONLY with the translated sentence.
            - Do NOT add quotes, bullets, dashes, or extra characters.
            - Do NOT add explanations or notes.
            - Do NOT translate WiFi names, passwords, or any proper nouns.
            - Keep the structure of the sentence natural in the target language.
            - Output MUST be a single clean sentence.

            Text to translate:
            %s
            """.formatted(lang, text);

        AiRequest request = new AiRequest(model, prompt);

        AgentResponse response = webClient.post()
                .uri(aiUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AgentResponse.class)
                .block();

        if (response == null || response.getResponse() == null) {
            return text;
        }

        return response.getResponse().trim()
                .replace("«", "")
                .replace("»", "")
                .replace("\"", "")
                .replace("'", "")
                .replace("。", "。");
    }




    public String translate(String text, String lang) {
        if (lang.equals("es")) return text;
        return strictTranslate(text, lang);
    }

    public String classifyIntent(String message) {

        String prompt = """
            You are an intent classifier for a vacation rental assistant.

            Classify the user's message into EXACTLY one of these intents:
            - CHECK_IN
            - CHECK_OUT
            - WIFI
            - RULES
            - ADDRESS
            - UNKNOWN

            RULES:
            - If the message is asking for nearby places, recommendations, supermarkets, restaurants, or anything NOT directly related to the property → respond UNKNOWN.
            - If the message mentions proximity, distance, or “nearby”, this is NOT ADDRESS → respond UNKNOWN.
            - ADDRESS is ONLY for questions explicitly asking for the property's address or location.
            - Respond ONLY with the intent name. No explanations.

            Message:
            "%s"
            """.formatted(message);

        AiRequest request = new AiRequest(model, prompt);

        AgentResponse response = webClient.post()
                .uri(aiUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AgentResponse.class)
                .block();

        if (response == null || response.getResponse() == null) {
            return "UNKNOWN";
        }

        return response.getResponse().trim().toUpperCase();
    }


}
