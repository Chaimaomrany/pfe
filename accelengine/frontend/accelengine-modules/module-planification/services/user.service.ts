import { Injectable, Injector } from '@angular/core';
import { APP_CONFIG } from '@app/app.config';
import { CrudAPIService } from 'accelengine-lib';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class UserService extends CrudAPIService<User>{

  constructor(
    private injector: Injector) {
    super(injector);
    this.endpointService = APP_CONFIG.apiBaseUrl + '/user';
  }

  findByCode(code: String): Observable<User> {
    return this.http.get<User>(`${this.endpointService}/findbycode/${code}`);
  }

  findUserByAccountId(accountId: number): Observable<User> {
    return this.http.get<User>(`${this.endpointService}/userbyaccount/${accountId}`);
  }

  findAllByRoles(listRoles: number[]): Observable<User[]> {
    return this.http.get<User[]>(`${this.endpointService}/findallbyroles/${listRoles}`);
  }

  findAllByNodeAndRole(idNode: Number, role: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.endpointService}/findallbynodeandrole/${role}/${idNode}`);
  }
  getAllTreesOfNodesByUser(codeUser: string): Observable<Node[]> {
    const url = `${this.endpointService}/getalltreesofnodesbyuser/${codeUser}`;
    return this.http.get<Node[]>(url);
  }

//   findAllByAbilitys(listAbilitys: number[]): Observable<User[]> {
//     return this.http.get<User[]>(`${this.endpointService}/findallbyabilitys/${listAbilitys}`);
//   }
}
