package com.hostpilot.language;

import com.hostpilot.service.LanguageService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LanguageServiceTest {

    LanguageService service = new LanguageService();

    @Test
    void testDetectChinese() {
        assertEquals("zh", service.detectLanguage("你好"));
    }

    @Test
    void testDetectFrench() {
        assertEquals("fr", service.detectLanguage("Bonjour"));
    }

    @Test
    void testDetectSpanish() {
        assertEquals("es", service.detectLanguage("Hola"));
    }
}
