package com.hostpilot.template;

import com.hostpilot.intent.Intent;
import com.hostpilot.model.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TemplateServiceTest {

    TemplateService service = new TemplateService();

    private Property property;

    @BeforeEach
    void setUp() {
        service = new TemplateService();

        property = new Property();
        property.setWifiName("MiWifi");
        property.setWifiPassword("12345678");
        property.setAddress("Calle Mayor 123, Madrid");
        property.setCheckIn("15:00");
        property.setCheckOut("11:00");
        property.setRules("No fumar. No fiestas.");
    }

    @Test
    void testWifiTemplate() {
        String result = service.generateTemplate(Intent.WIFI, property);
        assertTrue(result.contains("La red wifi es"));
    }

    @Test
    void testCheckInTemplate() {
        String result = service.generateTemplate(Intent.CHECK_IN, property);
        assertTrue(result.contains("El check-in es"));
    }
}
