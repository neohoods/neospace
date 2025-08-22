import { Observable } from 'rxjs';
import { UIUser } from '../../../models/UIUser';

export interface UsersService {
    getUser(userId: string): Observable<UIUser>;
    updateUser(user: UIUser): Observable<UIUser>;
}
