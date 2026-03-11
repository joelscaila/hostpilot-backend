package com.hostpilot.controller;

import com.hostpilot.dto.AgentReply;
import com.hostpilot.dto.AgentRequest;
import com.hostpilot.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @PostMapping("/reply")
    public AgentReply reply(@RequestBody AgentRequest request) {
        return agentService.replyToGuest(request.getPropertyId(), request.getMessage());
    }
}
