package br.com.nexfleet.shared.api;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ApiInfoController {

    @GetMapping
    public Map<String, String> getApiInfo() {
        return Map.of(
                "name", "NexFleet API",
                "version", "v1",
                "architecture", "vertical-slice-clean-architecture"
        );
    }
}

