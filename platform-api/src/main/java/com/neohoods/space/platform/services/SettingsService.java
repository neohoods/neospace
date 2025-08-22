package com.neohoods.space.platform.services;

import org.springframework.stereotype.Service;

import com.neohoods.space.platform.entities.SettingsEntity;
import com.neohoods.space.platform.model.GetPublicSettings200Response;
import com.neohoods.space.platform.model.GetSecuritySettings200Response;
import com.neohoods.space.platform.model.SaveSecuritySettingsRequest;
import com.neohoods.space.platform.repositories.SettingsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SettingsService {
        private final SettingsRepository settingsRepository;

        public Mono<GetPublicSettings200Response> getPublicSettings() {
                SettingsEntity setting = settingsRepository.findTopByOrderByIdAsc().get();

                return Mono.just(new GetPublicSettings200Response()
                        .isRegistrationEnabled(setting.isRegistrationEnabled())
                        .ssoEnabled(setting.isSsoEnabled())
                );
        }

        public Mono<GetSecuritySettings200Response> getSecuritySettings() {
                SettingsEntity setting = settingsRepository.findTopByOrderByIdAsc().get();

                return Mono.just(new GetSecuritySettings200Response()
                                .isRegistrationEnabled(setting.isRegistrationEnabled())
                                .ssoEnabled(setting.isSsoEnabled())
                                .ssoClientId(setting.getSsoClientId())
                                .ssoClientSecret(setting.getSsoClientSecret())
                                .ssoTokenEndpoint(setting.getSsoTokenEndpoint())
                                .ssoAuthorizationEndpoint(setting.getSsoAuthorizationEndpoint())
                                .ssoScope(setting.getSsoScope()));
        }

        public Mono<SettingsEntity> saveSecuritySettings(SaveSecuritySettingsRequest request) {

                SettingsEntity setting = settingsRepository.findTopByOrderByIdAsc().get();

                setting = SettingsEntity.builder()
                                .id(setting.getId())
                                .ssoEnabled(request.getSsoEnabled())
                                .ssoClientId(request.getSsoClientId())
                                .ssoClientSecret(request.getSsoClientSecret())
                                .ssoTokenEndpoint(request.getSsoTokenEndpoint())
                                .ssoAuthorizationEndpoint(request.getSsoAuthorizationEndpoint())
                                .ssoScope(request.getSsoScope())
                                .build();
                return Mono.just(settingsRepository.save(setting));
        }
}