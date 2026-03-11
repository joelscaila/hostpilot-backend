package com.hostpilot.service;

import com.hostpilot.ai.AiService;
import com.hostpilot.dto.AgentReply;
import com.hostpilot.model.Property;
import com.hostpilot.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final PropertyRepository propertyRepository;
    private final AiService aiService;

    public AgentReply replyToGuest(Long propertyId, String message) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        String context = """
                Name: %s
                Address: %s
                Check-in: %s
                Check-out: %s
                Wifi: %s / %s
                Rules: %s
                Description: %s
                """.formatted(
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

        return new AgentReply(aiReply);
    }
}
