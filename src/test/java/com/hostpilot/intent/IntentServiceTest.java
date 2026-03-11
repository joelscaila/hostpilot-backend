package com.hostpilot.service;

import com.hostpilot.ai.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final AiService aiService;

    public String detectLanguage(String message) {

        String prompt = """
                Detect the language of the following message.
                Respond ONLY with the ISO 639-1 language code (like "es", "en", "fr", "zh", "ar", "de", "it", "ja", "ko").
                
                Message:
                "%s"
                """.formatted(message);

        String result = aiService.generateReply(prompt, "");

        return result.trim().toLowerCase();
    }
}
