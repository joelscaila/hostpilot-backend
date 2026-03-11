package com.hostpilot.controller;

import com.hostpilot.dto.AgentRequest;
import com.hostpilot.service.AgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/reply")
    public ResponseEntity<String> reply(@RequestBody AgentRequest req) {
        return ResponseEntity.ok(agentService.reply(req));
    }
}
