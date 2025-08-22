package com.neohoods.space.platform.api.publicapi.settings;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.neohoods.space.platform.api.SettingsPublicApiApiDelegate;
import com.neohoods.space.platform.model.GetPublicSettings200Response;
import com.neohoods.space.platform.model.GetSecuritySettings200Response;
import com.neohoods.space.platform.services.SettingsService;
import reactor.core.publisher.Mono;

@Service
public class SettingsPublicApi implements SettingsPublicApiApiDelegate {

    private final SettingsService settingsService;

    public SettingsPublicApi(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public Mono<ResponseEntity<GetPublicSettings200Response>> getPublicSettings(ServerWebExchange exchange) {
        return settingsService.getPublicSettings()
                .map(ResponseEntity::ok);
    }
}