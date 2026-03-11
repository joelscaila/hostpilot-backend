package com.hostpilot.agent;

import com.hostpilot.ai.AiService;
import com.hostpilot.dto.AgentReply;
import com.hostpilot.intent.Intent;
import com.hostpilot.intent.IntentService;
import com.hostpilot.model.Property;
import com.hostpilot.repository.PropertyRepository;
import com.hostpilot.service.AgentService;
import com.hostpilot.service.LanguageService;
import com.hostpilot.service.MemoryService;
import com.hostpilot.template.TemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AgentServiceTest {

    private PropertyRepository propertyRepository;
    private AiService aiService;
    private MemoryService memoryService;
    private IntentService intentService;
    private TemplateService templateService;
    private LanguageService languageService;
    private AgentService agentService;

    private Property property;

    @BeforeEach
    void setUp() {

        // Mocks
        propertyRepository = Mockito.mock(PropertyRepository.class);
        aiService = Mockito.mock(AiService.class);
        memoryService = Mockito.mock(MemoryService.class);
        templateService = Mockito.mock(TemplateService.class);

        // Servicios reales
        languageService = new LanguageService();
        intentService = new IntentService(aiService);

        // Servicio principal
        agentService = new AgentService(
                propertyRepository,
                aiService,
                memoryService,
                intentService,
                templateService,
                languageService
        );

        // Property simulada
        property = new Property();
        property.setId(1L);
        property.setWifiName("MiWifi");
        property.setWifiPassword("12345678");
        property.setAddress("Calle Mayor 123, Madrid");
        property.setCheckIn("15:00");
        property.setCheckOut("11:00");
        property.setRules("No fumar. No fiestas.");
        property.setDescription("Un bonito apartamento en el centro.");

        Mockito.when(propertyRepository.findById(1L))
                .thenReturn(Optional.of(property));
    }

    @Test
    void testWifiFlowInChinese() {

        // 1. La IA clasifica la intención
        Mockito.when(aiService.classifyIntent("请问WiFi密码是多少？"))
                .thenReturn("WIFI");

        // 2. La plantilla generada
        Mockito.when(templateService.generateTemplate(Intent.WIFI, property))
                .thenReturn("La red wifi es MiWifi y la contraseña es 12345678.");

        // 3. Traducción al chino
        Mockito.when(aiService.translate(
                "La red wifi es MiWifi y la contraseña es 12345678.",
                "zh"
        )).thenReturn("WiFi网络名称是 MiWifi，密码是 12345678。");

        // 4. Historial vacío
        Mockito.when(memoryService.getHistory(1L))
                .thenReturn(List.of());

        // 5. Ejecutamos el flujo real
        AgentReply reply = agentService.replyToGuest(1L, "请问WiFi密码是多少？");

        // 6. Validamos
        assertEquals("WiFi网络名称是 MiWifi，密码是 12345678。", reply.getReply());
    }
}
