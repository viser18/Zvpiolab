package com.example.server.controllers;

import com.example.server.entities.User;
import com.example.server.models.TicketResponse;
import com.example.server.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateTicket(
            @RequestBody Map<String, String> request,
            @AuthenticationPrincipal User user) {
        try {
            String licenseCode = request.get("activationKey");
            String macAddress = request.get("macAddress");

            if (licenseCode == null || licenseCode.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "License code is required"));
            }
            if (macAddress == null || macAddress.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "MAC address is required"));
            }

            TicketResponse response = ticketService.generateTicket(
                    licenseCode,
                    macAddress,
                    user.getId()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/license/{licenseId}/device/{deviceId}")
    public ResponseEntity<?> generateTicketByIds(
            @PathVariable Long licenseId,
            @PathVariable Long deviceId,
            @AuthenticationPrincipal User user) {
        try {
            TicketResponse response = ticketService.generateTicketByLicenseId(
                    licenseId,
                    deviceId,
                    user.getId()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}