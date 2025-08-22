import { InjectionToken, Provider } from '@angular/core';
import { ConfigService } from '../../services/config.service';
import { MockUsersService } from './services/mock/users.service';
import { APIUsersService } from './services/real-api/users.service';
import { UsersService } from './services/users.service';

export const USERS_SERVICE_TOKEN = new InjectionToken<UsersService>(
    'UsersService',
);

export const hubProviders: Provider[] = [
    {
        provide: USERS_SERVICE_TOKEN,
        useExisting: ConfigService.configuration.useMockApi
            ? MockUsersService
            : APIUsersService,
    },
];
