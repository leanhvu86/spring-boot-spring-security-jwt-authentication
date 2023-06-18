package com.trunggame.controllers;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.trunggame.dto.WebhookPayloadDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/facebook")
public class FacebookWebhookController {

    private static final String ACCESS_TOKEN = "YOUR_FACEBOOK_ACCESS_TOKEN";
    private static final String GROUP_ID = "YOUR_FACEBOOK_GROUP_ID";

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody WebhookPayloadDTO payload) {
        String message = payload.getMessage();
        if (message != null && !message.isEmpty()) {
            // Send the message to the Facebook group
            FacebookClient client = new DefaultFacebookClient(ACCESS_TOKEN, Version.LATEST);
            client.publish(GROUP_ID + "/feed", String.class,
                    Parameter.with("message", message));

            return ResponseEntity.ok("Message sent successfully to the Facebook group.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payload.");
        }
    }
}

