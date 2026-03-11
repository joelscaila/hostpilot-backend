package com.hostpilot.service;

import com.hostpilot.ai.AiService;
import com.hostpilot.dto.AgentReply;
import com.hostpilot.dto.ChatMessage;
import com.hostpilot.model.Property;
import com.hostpilot.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final PropertyRepository propertyRepository;
    private final AiService aiService;
    private final MemoryService memoryService;

    public AgentReply replyToGuest(Long propertyId, String message) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // Guardar mensaje del huésped
        memoryService.addMessage(propertyId, new ChatMessage("guest", message));

        // Construir historial
        StringBuilder historyText = new StringBuilder();
        for (ChatMessage msg : memoryService.getHistory(propertyId)) {
            historyText.append(msg.getRole()).append(": ").append(msg.getContent()).append("\n");
        }

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
                historyText,
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

        // Guardar respuesta del agente
        memoryService.addMessage(propertyId, new ChatMessage("agent", aiReply));

        return new AgentReply(aiReply);
    }
}

