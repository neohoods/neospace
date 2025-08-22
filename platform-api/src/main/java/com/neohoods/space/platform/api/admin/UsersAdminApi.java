package com.neohoods.space.platform.api.admin;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.neohoods.space.platform.api.UsersAdminApiApiDelegate;
import com.neohoods.space.platform.model.SetUserPasswordRequest;
import com.neohoods.space.platform.model.UserWithStats;
import com.neohoods.space.platform.services.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersAdminApi implements UsersAdminApiApiDelegate {
    private final UsersService usersService;

    @Override
    public Mono<ResponseEntity<UserWithStats>> getUser(UUID userId, ServerWebExchange exchange) {
        return usersService.getUserWithStats(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<UserWithStats>>> getUsers(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(usersService.getUsersWithStats()));
    }

    @Override
    public Mono<ResponseEntity<UserWithStats>> saveUser(Mono<UserWithStats> userWithStats, ServerWebExchange exchange) {
        return userWithStats.flatMap(usersService::saveUser)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> setUserPassword(UUID userId, Mono<SetUserPasswordRequest> setUserPasswordRequest,
            ServerWebExchange exchange) {
        return setUserPasswordRequest.doOnNext(request -> usersService.setUserPassword(userId, request.getNewPassword()))
                .map(r -> ResponseEntity.noContent().build());
    }
}