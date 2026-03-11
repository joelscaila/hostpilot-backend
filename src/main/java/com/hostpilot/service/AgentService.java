package com.hostpilot.service;

import com.hostpilot.ai.AiService;
import com.hostpilot.dto.AgentReply;
import com.hostpilot.dto.ChatMessage;
import com.hostpilot.intent.Intent;
import com.hostpilot.intent.IntentService;
import com.hostpilot.model.Property;
import com.hostpilot.repository.PropertyRepository;
import com.hostpilot.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final PropertyRepository propertyRepository;
    private final AiService aiService;
    private final MemoryService memoryService;
    private final IntentService intentService;
    private final TemplateService templateService;
    private final LanguageService languageService;

    public AgentReply replyToGuest(Long propertyId, String message) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // Detectar idioma del huésped
        String lang = languageService.detectLanguage(message);

        // Detectar intención
        Intent intent = intentService.detectIntent(message);

        // Si hay plantilla → traducir → devolver
        String template = templateService.generateTemplate(intent, property);
        if (template != null) {

            String translated = aiService.translate(template, lang);

            memoryService.addMessage(propertyId, new ChatMessage("guest", message));
            memoryService.addMessage(propertyId, new ChatMessage("agent", translated));

            return new AgentReply(translated);
        }

        // Si no hay plantilla → IA con memoria
        memoryService.addMessage(propertyId, new ChatMessage("guest", message));

        String history = memoryService.getHistory(propertyId).stream()
                .map(m -> m.getRole() + ": " + m.getContent())
                .reduce("", (a, b) -> a + b + "\n");

        String context = """
                Conversation history:
                %s

                Property:
                Name: %s
                Address: %s
                Check-in: %s
                Check-out: %s
                Wifi: %s / %s
                Rules: %s
                Description: %s
                """.formatted(
                history,
                property.getName(),
                property.getAddress(),
                property.getCheckIn(),
                property.getCheckOut(),
                property.getWifiName(),
                property.getWifiPassword(),
                property.getRules(),
                property.getDescription()
        );

        String aiReply = aiService.generateReply(context, message);

        memoryService.addMessage(propertyId, new ChatMessage("agent", aiReply));

        return new AgentReply(aiReply);
    }
}

