package com.hostpilot.template;

import com.hostpilot.ai.AiService;
import com.hostpilot.intent.Intent;
import com.hostpilot.intent.IntentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateServiceTest {

    @Test
    void testWifiInChinese() {
        AiService ai = Mockito.mock(AiService.class);
        Mockito.when(ai.classifyIntent("请问WiFi密码是多少？")).thenReturn("WIFI");

        IntentService service = new IntentService(ai);

        Intent result = service.detectIntent("请问WiFi密码是多少？");
        assertEquals(Intent.WIFI, result);
    }

    @Test
    void testFrenchSupermarketShouldBeUnknown() {
        AiService ai = Mockito.mock(AiService.class);
        Mockito.when(ai.classifyIntent("Y a-t-il un supermarché à proximité ?"))
                .thenReturn("UNKNOWN");

        IntentService service = new IntentService(ai);

        Intent result = service.detectIntent("Y a-t-il un supermarché à proximité ?");
        assertEquals(Intent.UNKNOWN, result);
    }
}
